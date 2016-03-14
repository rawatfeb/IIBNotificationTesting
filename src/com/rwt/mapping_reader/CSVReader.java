package com.rwt.mapping_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.rwt.beans.IdentificationObject;

public class CSVReader implements PublishNotificationMappingReader {

	public static void main(String[] args) throws Exception {
		String csvFilePath = "D:/Rawat/Mars_Workspace/NotificationTesting/TestCases/Perosn/ProducerCodeTestCase.csv"; // ProducerCodeTestCase
		// //SuffixTestCase
		System.out.println(new CSVReader().read(csvFilePath));
	}

	@Override
	public List<IdentificationObject> read(String csvFileString) throws Exception {
		// csvFileString =
		// "D:/Rawat/Mars_Workspace/NotificationTesting/SuffixTestCase.csv";
		File csvFile = new File(csvFileString);
		List<IdentificationObject> identificationObjectList = new ArrayList<IdentificationObject>();
		if (csvFile.isDirectory()) {
			File[] files = csvFile.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".csv");
				}
			});
			if (files.length != 0) {
				if (files.length > 1) {
					System.out.println("Seems like multiple file(Test Cases file) present in the directory=" + csvFileString
							+ " which is supposed to have only one file any one of them will be picked up for test execution");
				}
				identificationObjectList = read0(files[0]);
			} else {
				System.out.print(
						"No Test Case file present in the directory=" + csvFileString + " will return empty identificationObjectList. skipping. ");
				return identificationObjectList;
			}
		} else {
			identificationObjectList = read0(csvFile);
		}
		return identificationObjectList;
	}

	private List<IdentificationObject> read0(File file) throws Exception {
		List<IdentificationObject> identificationObjectList = new ArrayList<IdentificationObject>();
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(file));
			String line = null;
			while (null != (line = fileReader.readLine())) {
				String[] tokens = line.split(",");
				// order in csv file to be fixed
				if (tokens.length < 3) {
					System.out.println("Make sure your Test case CSV file is well formed. also make sure it does not have blank line like ,,,");
					throw new RuntimeException(file.getAbsolutePath()+
							" Column in csv file (TestCase) should be more than 3 and should follow the order as well (Notification XPATH, MDM Publish XPATH, Attribute Name, Attribute Value)");
				}
				IdentificationObject identificationObject = new IdentificationObject();
				identificationObject.setMdmNotificationXPath(tokens[0].trim());
				identificationObject.setMdmPublishXPath(tokens[1].trim());
				identificationObject.setValue(tokens[2].trim());
				if (tokens.length > 3) {
					identificationObject.setIdentificationNumber(tokens[3].trim());
				}
				identificationObjectList.add(identificationObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != fileReader) {
				fileReader.close();
			}
		}
		return identificationObjectList;
	}

}
