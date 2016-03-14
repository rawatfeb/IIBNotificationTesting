package com.rwt.util;

import static com.rwt.constant.Constants.NotificationBobj.PERSON_NOTIFICATION;

import java.util.LinkedList;
import java.util.List;

import com.rwt.beans.IdentificationObject;

public class Utils {

	
	
	public static List<IdentificationObject> createSampleIdentificationObjectList2() {

		List<IdentificationObject> identificationObjectList = new LinkedList<IdentificationObject>();
		String mdmNotificationXPath = "PersonNotificationBObj/TCRMPersonBObj/TCRMPersonNameBObj/Suffix";
		String notificationXPathValue = "Mrs.";
		String mdmPublishXPath = "//cus_info:MDMPublish/cus_info:customerNotification/cus_info:customer/cus:legalName/cus:suffix";
		String identificationNumber = "67676767";

		IdentificationObject identificationObject = new IdentificationObject();
		identificationObject.setIdentificationNumber(identificationNumber);
		identificationObject.setMdmNotificationXPath(mdmNotificationXPath);
		identificationObject.setMdmPublishXPath(mdmPublishXPath);
		identificationObject.setValue(notificationXPathValue);
		
		identificationObject.setNotificationType(PERSON_NOTIFICATION);
		
		identificationObjectList.add(identificationObject);

		return identificationObjectList;
	}
	
	
	public static List<IdentificationObject> createSampleIdentificationObjectList() {

		List<IdentificationObject> identificationObjectList = new LinkedList<IdentificationObject>();
		String mdmNotificationXPath = "PersonNotificationBObj/TCRMPersonBObj/TCRMPersonNameBObj/Suffix";
		String notificationXPathValue = "Mrs.";
		String mdmPublishXPath = "//cus_info:MDMPublish/cus_info:customerNotification/cus_info:customer/cus:legalName/cus:suffix";
		String identificationNumber = "67676767";

		IdentificationObject identificationObject = new IdentificationObject();
		identificationObject.setIdentificationNumber(identificationNumber);
		identificationObject.setMdmNotificationXPath(mdmNotificationXPath);
		identificationObject.setMdmPublishXPath(mdmPublishXPath);
		identificationObject.setValue(notificationXPathValue);
		
		identificationObject.setNotificationType(PERSON_NOTIFICATION);
		
		identificationObjectList.add(identificationObject);

		return identificationObjectList;
	}

}
