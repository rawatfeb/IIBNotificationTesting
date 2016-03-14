package com.rwt.mapping_reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.rwt.beans.IdentificationObject;

public class ExcelReader implements PublishNotificationMappingReader {

	@Override
	public List<IdentificationObject> read(String excelFileString) throws Exception{
		excelFileString = "D:/Rawat/SOA/IIB Pub Sub Notifications new version(v2)/Publish Notification Mapping Sheet - Copy.csv";
		File csvFile = new File(excelFileString);
		BufferedReader fileReader = null;
		List<IdentificationObject> identificationObjectList = new ArrayList<IdentificationObject>();
		try {
			fileReader = new BufferedReader(new FileReader(csvFile));
			String line = null;
			while (null != (line = fileReader.readLine())) {
				String[] tokens = line.split(",");
				// order in csv file to be fixed
				if (tokens.length < 19) {
					throw new RuntimeException(
							"Column in csv file (TestCase) should be more than 3 and should follow the order as well");
				}
				IdentificationObject identificationObject = new IdentificationObject();
				identificationObject.setMdmNotificationXPath(tokens[14].trim());
				identificationObject.setMdmPublishXPath(tokens[18].trim());
				identificationObject.setValue(tokens[19].trim());
				if (tokens.length > 3) {
					identificationObject.setIdentificationNumber(tokens[3].trim());
				}
				identificationObjectList.add(identificationObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			fileReader.close();
		}
		return identificationObjectList;
	}

}
