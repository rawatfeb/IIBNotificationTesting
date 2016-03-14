package com.rwt.test;

import java.sql.Timestamp; 
import java.util.Date; 

import com.ibm.mq.MQC; 
import com.ibm.mq.MQEnvironment; 
import com.ibm.mq.MQException; 
import com.ibm.mq.MQGetMessageOptions; 
import com.ibm.mq.MQMessage; 
import com.ibm.mq.MQPutMessageOptions; 
import com.ibm.mq.MQQueue; 
import com.ibm.mq.MQQueueManager; 


public class MQTest { 

   /** 
    * @param args 
    */ 
   public String ConnectToMQ(String buildingInsNo) { 

      MQEnvironment.hostname   = "localhost"; 
      MQEnvironment.channel   = "IB9QMGR.CLNT"; 
      MQEnvironment.port      = Integer.parseInt("1414"); 
      String qManager = "IB9QMGR"; 
      String SendQ = "MDM.NOTIFICATION.XML"; 
      String ReceiveQ = "MDM.PUB.BKOUT.XML"; 
      Timestamp obj = new Timestamp(new Date().getTime()); 
      byte[] msgid_byte = null ; 
      String var = "Didn't get it yet"; 
       
      /***************************************************************************************************************/ 
      /*                   Putting the message manually with message id and correlation id                           */ 
      /***************************************************************************************************************/ 
      try { 
         MQQueueManager qMgr = new MQQueueManager(qManager); 
         int openOptions = MQC.MQOO_OUTPUT ; 
          MQQueue SDLQ = qMgr.accessQueue(SendQ,openOptions, null, null, null); 
          MQMessage message = new MQMessage(); 
          message.writeUTF(buildingInsNo); 
          MQPutMessageOptions pmo = new MQPutMessageOptions(); 
          msgid_byte = message.messageId; 
          SDLQ.put(message,pmo); 
          SDLQ.close(); 
          qMgr.disconnect(); 
      } 
      catch (MQException ex) { 
          System.err.println("An MQ error occurred : Completion code " + 
                             ex.completionCode + 
                             " Reason code " + ex.reasonCode); 
        } catch (java.io.IOException ex) { 
          System.err.println("An error occurred writing to message buffer: " + 
                             ex); 
        } 
/***************************************************************************************************************/ 
/*                             Getting the message from Q1                                                     */ 
/***************************************************************************************************************/ 
        
        try { 
         MQQueueManager qMngr = new MQQueueManager(qManager); 
         int openOptions = MQC.MQOO_INPUT_EXCLUSIVE | MQC.MQOO_BROWSE; 
         MQQueue myQueue = qMngr.accessQueue(ReceiveQ, openOptions,null, null, null); 
         /******************************************************/ 
            /* Set up our options to browse for the first message */ 
            /******************************************************/ 
            MQGetMessageOptions gmo = new MQGetMessageOptions(); 
            gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST; 
            MQMessage myMessage = new MQMessage(); 
            boolean done = false; 
            do { 
               try { 
                  /*****************************************/ 
                   /* Reset the message and IDs to be empty */ 
                   /*****************************************/ 
                   myMessage.clearMessage(); 
                   myMessage.correlationId = MQC.MQCI_NONE; 
                   myMessage.messageId = MQC.MQMI_NONE; 
                   myQueue.get(myMessage, gmo); 
                   byte[] corr_byte = myMessage.correlationId; 
                   var = myMessage.readLine(); 
                   System.err.println("Browsed message: " + var); 
                   System.err.println(">>>>>>>> correlationID  = "+corr_byte); 
                   System.err.println(">>>>>>>> msgid.getbytes = "+ msgid_byte); 
                   if (corr_byte.equals(msgid_byte)) { 
                       System.err.println(">>>>>>>>>> FOUND <<<<<<<<<<"); 
                       gmo.options = MQC.MQGMO_MSG_UNDER_CURSOR; 
                       myQueue.get(myMessage, gmo); 
                       var = myMessage.readLine(); 
                    } 
                   /************************************************/ 
                   /* Reset the options to browse the next message */ 
                   /************************************************/ 
                   gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT; 
               }catch (MQException ex) { 
                   /**************************************************/ 
                   /* Probably encountered our no message available: */ 
                   /* write out error and mark loop to be exited     */ 
                   /**************************************************/ 
                   System.out.println("MQ exception: CC = " + ex.completionCode 
                                      + " RC = " + ex.reasonCode); 
                   done = true; 
                } catch (java.io.IOException ex) { 
                   System.out.println("Java exception: " + ex); 
                   done = true; 
                } 

            }while(!done); 
             

            /**********************************************************/ 
            /* Before the program ends, the open queue will be closed */ 
            /* and the queue manager will be disconnected.            */ 
            /**********************************************************/ 
            myQueue.close(); 
            qMngr.disconnect(); 
             
      } catch (MQException e) { 
         e.printStackTrace(); 
      } 
        
      return var; 
   } 
    
   public static void main(String[] args) { 
      // TODO Auto-generated method stub 
	   MQTest obj = new MQTest(); 
      System.err.println(); 
      System.err.println(obj.ConnectToMQ("000000199")); 
       
   } 

} 