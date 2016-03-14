package com.rwt.mapping_reader;

import java.util.List;

import com.rwt.beans.IdentificationObject;

public interface PublishNotificationMappingReader {

	public List<IdentificationObject> read(String csvFileString) throws Exception;

}
