package com.rwt.util;

import static com.rwt.constant.Constants.COMMA;
import static com.rwt.constant.Constants.RESULT_FILE_NAME;
import static com.rwt.constant.Constants.TEST_CASES_DIRECTORY;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.rwt.beans.IdentificationObject;
import com.rwt.constant.Constants.NotificationsType;


public class CSVMappingWriter {

	public static void main(String[] args) {
		List<IdentificationObject> identificationObjectList = Utils.createSampleIdentificationObjectList();
		System.out.println(identificationObjectList);
		writeCSV(identificationObjectList);
	}

	public static void writeCSV(List<IdentificationObject> identificationObjectList) {
		for (IdentificationObject identificationObject : identificationObjectList) {
			writeCSV(identificationObject);
		}
	}

	public static void writeCSV(IdentificationObject identificationObject) {

		String resultFile = null;
		switch (identificationObject.getNotificationType()) {
		case PERSON_NOTIFICATION:
			resultFile = TEST_CASES_DIRECTORY + File.separator + NotificationsType.PERSON + File.separator
					+ RESULT_FILE_NAME;
			break;
		case POLICY_NOTIFICATION:
			resultFile = TEST_CASES_DIRECTORY + File.separator + NotificationsType.POLICY + File.separator
					+ RESULT_FILE_NAME;
			break;
		case GROUPING_NOTIFICATION:
			resultFile = TEST_CASES_DIRECTORY + File.separator + NotificationsType.GROUPING + File.separator
					+ RESULT_FILE_NAME;
			break;
		case BILLING_NOTIFICATION:
			resultFile = TEST_CASES_DIRECTORY + File.separator + NotificationsType.BILLING + File.separator
					+ RESULT_FILE_NAME;
			break;
		case COLLAPSE_NOTIFICATION:
			resultFile = TEST_CASES_DIRECTORY + File.separator + NotificationsType.COLLAPSE + File.separator
					+ RESULT_FILE_NAME;
			break;
		case UNMERGE_NOTIFICATION:
			resultFile = TEST_CASES_DIRECTORY + File.separator + NotificationsType.UNMERGE + File.separator
					+ RESULT_FILE_NAME;
			break;
		}

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(resultFile, true);
			String line = getCSLineStringFromIdentificationObj(identificationObject);
			fos.write(line.getBytes());
			fos.write("\r\n".getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getCSLineStringFromIdentificationObj(IdentificationObject identificationObject) {
		return identificationObject.getMdmNotificationXPath() + COMMA
				+ identificationObject.getMdmPublishXPath() + COMMA + identificationObject.getValue()
				+ COMMA + identificationObject.getIdentificationNumber();
	}
}
