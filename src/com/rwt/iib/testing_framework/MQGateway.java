package com.rwt.iib.testing_framework;

import static com.rwt.constant.Constants.NOTIFICATION_QUEUE;
import static com.rwt.constant.Constants.PUB_BACKOUT_QUEUE;
import static com.rwt.constant.Constants.SUB_QUEUE;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.rwt.beans.Environment;
import com.rwt.beans.IdentificationObject;
import com.rwt.parser.DocumentFormator;
import com.rwt.parser.DocumentReader;
import com.rwt.util.CSVMappingWriter;
import com.rwt.util.FileReadUtil;

public class MQGateway {

	private MQQueueManager queueManager = null;
	private MQQueue queue;
	private MQMessage sendmsg;
	private MQPutMessageOptions pmo = new MQPutMessageOptions();
	private boolean DEBUG;

	public MQGateway(Environment env) throws Exception {
		init(env);
	}

	public void putMessageToNotificationQueue(String message_dir, List<IdentificationObject> identificationObjectList)
			throws Exception {
		connectToQ(NOTIFICATION_QUEUE);
		// Define a simple MQ message
		sendmsg = new MQMessage();
		formatMQMessage();
		File[] listOfMessages = FileReadUtil.listMessages(message_dir);
		if (listOfMessages.length >= 1) {
			pushAllMessages(listOfMessages, identificationObjectList);
		} else {
			System.out.println(
					"No Sample Notification in " + message_dir + " skipping Test for " + identificationObjectList);
		}
	}

	public void init(Environment e) throws Exception {
		// Set MQ connection credentials to MQ Envorinment.
		MQEnvironment.hostname = e.getHostName();
		MQEnvironment.channel = e.getChannel();
		MQEnvironment.port = e.getPort();
		MQEnvironment.userID = e.getUserID();
		MQEnvironment.password = e.getPassword();
		queueManager = new MQQueueManager(e.getQueueManager());
		// set transport properties.
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);

		// Set 64 bit MQ library path if there is any compatibility Issue.
		// Runtime.getRuntime()
		// .exec("cmd set MQ_JAVA_LIB_PATH=C:\\Program Files
		// (x86)\\IBM\\WebSphere MQ\\java\\lib64;");
		System.out.println("Connected to " + e);
	}

	private void connectToQ(String q) throws MQException {
		int openOptions = CMQC.MQOO_OUTPUT + CMQC.MQOO_FAIL_IF_QUIESCING;
		// Access Remote Queue
		queue = queueManager.accessQueue(q, openOptions, null, // default q
																// manager
				null, // no dynamic q name
				null); // no alternate user id

		System.out.println("Connected to " + q);
	}

	private void formatMQMessage() throws MQException {
		sendmsg.format = CMQC.MQFMT_STRING;
		sendmsg.feedback = CMQC.MQFB_NONE;
		sendmsg.messageType = CMQC.MQMT_DATAGRAM;
		sendmsg.replyToQueueManagerName = queueManager.getName().toString();
	}

	public void closeQManager() throws MQException, IOException {
		queue.close();
		queueManager.disconnect();
		System.out.println("QManager Connection Closed and disconnected");
	}

	private void pushAllMessages(File[] listOfMessages, List<IdentificationObject> identificationObjectList)
			throws IOException, MQException, TransformerException {
		int count = 0;
		for (; count < listOfMessages.length; count++) {
			IdentificationObject identificationObject = null;
			if (count < identificationObjectList.size()) {
				identificationObject = identificationObjectList.get(count);
			}
			pushMessage(listOfMessages[count].getAbsolutePath(), identificationObject);
		}
	}

	public static void main(String[] args) {
		List<String> test = new ArrayList<String>();
		test.add("hello");
		System.out.println(test.get(0));
		System.out.println(test.get(1));
	}

	public void readMessageIdentificationNo(List<IdentificationObject> identificationObjectList) throws Exception {
		int openOptions = MQC.MQOO_INPUT_EXCLUSIVE | MQC.MQOO_BROWSE;
		MQQueue subQueue = queueManager.accessQueue(SUB_QUEUE, openOptions, null, null, null);
		System.out.println("Connected to Subscription Queue " + SUB_QUEUE);

		/******************************************************/
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
		MQMessage myMessage = new MQMessage();
		boolean done = false;
		DocumentReader documentReader = new DocumentReader(identificationObjectList);
		documentReader.setDEBUG(DEBUG);
		do {
			try {
				/*****************************************/
				/* Reset the message and IDs to be empty */
				/*****************************************/
				myMessage.clearMessage();
				myMessage.correlationId = MQC.MQCI_NONE;
				myMessage.messageId = MQC.MQMI_NONE;
				subQueue.get(myMessage, gmo);
				byte[] msg_bytes = new byte[myMessage.getMessageLength()]; // myMessage.readLine();
				myMessage.readFully(msg_bytes);
				done = documentReader.matchPublishNotification(msg_bytes);
				/************************************************/
				/* Reset the options to browse the next message */
				/************************************************/
				gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
				if (DEBUG) {
					System.out.print(".");
				}
			} catch (MQException | IOException ex) {
				/**************************************************/
				/* Probably encountered our no message available: */
				/* write out error and mark loop to be exited */
				/**************************************************/
				if (DEBUG) {
					System.out.println("MQ exception: CC = " + ex);
				}
				if (!done) {
					System.out.println(
							"\n\n\n >>>>>>>>>>>  Did not find in Subscription Queue.  Please review these attributes: "
									+ identificationObjectList + " \n\n\n");
					CSVMappingWriter.writeCSV(identificationObjectList);
					checkInPubBkoutQueue(identificationObjectList);
				}
				done = true;
			}
		} while (!done);
		subQueue.close();
	}

	private void checkInPubBkoutQueue(List<IdentificationObject> identificationObjectList) throws Exception {
		int openOptions = MQC.MQOO_INPUT_EXCLUSIVE | MQC.MQOO_BROWSE;
		MQQueue pubBKoutQueue = queueManager.accessQueue(PUB_BACKOUT_QUEUE, openOptions, null, null, null);
		System.out.println("Connected to " + PUB_BACKOUT_QUEUE);

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
				pubBKoutQueue.get(myMessage, gmo);
				byte[] msg_bytes = new byte[myMessage.getMessageLength()]; // myMessage.readLine();
				myMessage.readFully(msg_bytes);
				DocumentFormator documentFormator = new DocumentFormator(msg_bytes);
				done = documentFormator.matchNotification(identificationObjectList);
				/************************************************/
				/* Reset the options to browse the next message */
				/************************************************/
				gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
				if (DEBUG) {
					System.out.print(".");
				}
			} catch (MQException | IOException ex) {
				/**************************************************/
				/* Probably encountered our no message available: */
				/* write out error and mark loop to be exited */
				/**************************************************/
				if (DEBUG) {
					System.out.println("MQ exception: CC = " + ex);
				}
				if (!done) {
					System.out.println(
							"\n\n\n >>>>>>>>>>> Did not find the notification in " + PUB_BACKOUT_QUEUE + "\n\n\n");
				}
				done = true;
			}
		} while (!done);
		pubBKoutQueue.close();
	}

	public String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	private void pushMessage(String path, IdentificationObject identificationObject)
			throws IOException, MQException, TransformerException {
		DocumentFormator documentFormator = new DocumentFormator(path);
		documentFormator.setValue(identificationObject.getMdmNotificationXPath(), identificationObject.getValue());
		documentFormator.setIdentificationNumber(identificationObject.getIdentificationNumber());
		identificationObject.setNotificationType(documentFormator.getNotificationType());
		String correlationId = Long.valueOf(System.currentTimeMillis()).toString();
		sendmsg.correlationId = correlationId.getBytes();
		sendmsg.writeString(documentFormator.getDocumentAsString());
		queue.put(sendmsg, pmo);
		sendmsg.clearMessage();
		formatMQMessage();

		System.out.println("Message with IdentificationNumber = " + documentFormator.getIdentificationNumber()
				+ "  and correlationId " + correlationId + "("
				+ String.format("%040x", new BigInteger(1, correlationId.getBytes("UTF-8")))
				+ ") was pushed successfully \n ");

		if (DEBUG) {
			System.out.println(documentFormator.getDocumentAsString());
		}
	}

	public boolean isDEBUG() {
		return DEBUG;
	}

	public void setDEBUG(boolean dEBUG) {
		DEBUG = dEBUG;
	}
}
