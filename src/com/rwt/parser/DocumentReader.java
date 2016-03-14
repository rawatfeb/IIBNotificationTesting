package com.rwt.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rwt.beans.IdentificationObject;
import static com.rwt.constant.Constants.NotificationBobj.PERSON_NOTIFICATION;

public class DocumentReader {

	private List<IdentificationObject> identificationObjectList;
	private boolean DEBUG;

	public DocumentReader(List<IdentificationObject> identificationObjectList) throws Exception {
		this.identificationObjectList = identificationObjectList;
	}

	public static void main(String[] args) throws Exception {
		String nt = "D:/Rawat/Mars_Workspace/NotificationTesting/MDM_Publish_Person_Notification_With_BW_Attribute.xml";
		byte[] byteArray = Files.readAllBytes(Paths.get(nt));

		String mdmNotificationXPath = "PersonNotificationBObj/TCRMPersonBObj/TCRMPersonNameBObj/Suffix";
		String notificationXPathValue = "Suffix Test Passed";
		String mdmPublishXPath = "//cus_info:MDMPublish/cus_info:customerNotification/cus_info:customer/cus:legalName/cus:suffix";
		String identificationNumber = "104469";

		List<IdentificationObject> identificationObjectList = new ArrayList<IdentificationObject>();
		IdentificationObject identificationObject = new IdentificationObject();
		identificationObject.setIdentificationNumber(identificationNumber);
		identificationObject.setMdmNotificationXPath(mdmNotificationXPath);
		identificationObject.setMdmPublishXPath(mdmPublishXPath);
		identificationObject.setValue(notificationXPathValue);

		identificationObject.setNotificationType(PERSON_NOTIFICATION);

		IdentificationObject identificationObject2 = new IdentificationObject();
		identificationObject2.setIdentificationNumber("104469");
		identificationObject2
				.setMdmNotificationXPath("PersonNotificationBObj/TCRMPersonBObj/TCRMPersonNameBObj/firstName");
		identificationObject2.setMdmPublishXPath(
				"//cus_info:MDMPublish/cus_info:customerNotification/cus_info:customer/cus:legalName/cus:firstName");
		identificationObject2.setValue("Justine");
		identificationObject2.setNotificationType(PERSON_NOTIFICATION);
		identificationObjectList.add(identificationObject2);

		identificationObjectList.add(identificationObject);

		DocumentReader documentReader = new DocumentReader(identificationObjectList);
		System.out.println(documentReader.matchPublishNotification(byteArray));
	}

	public boolean matchPublishNotification(byte[] byteArray) throws Exception {
		InputStream inputStream = new ByteArrayInputStream(byteArray);
		XPathExtractor xPathExtractor = new XPathExtractor();
		xPathExtractor.init(inputStream);

		boolean found = false;
		Iterator<IdentificationObject> itr = identificationObjectList.iterator();
		while (itr.hasNext()) {
			IdentificationObject identificationObject = itr.next();
			List<String> xPaths = identificationObject.getxPaths();
			List<String> expectedValues = identificationObject.getExpectedValues();
			found = xPathExtractor.matchValuesByXpath(xPaths, expectedValues);
			if (found) {
				System.out.println("\n\n >>>>>>>>>>Found<<<<<<<<<<<<  Test Passed.  \n\n" + identificationObject);

				if (DEBUG) {
					System.out.println("Published Message :  " + new String(byteArray));
				}
				itr.remove();
			}
		}
		return found;
	}

	public void setDEBUG(boolean dEBUG) {
		this.DEBUG = dEBUG;
	}
}
