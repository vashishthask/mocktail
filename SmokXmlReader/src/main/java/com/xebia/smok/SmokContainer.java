package com.xebia.smok;

import com.xebia.smok.repository.DiskObjectRepository;
import com.xebia.smok.repository.ObjectRepository;
import com.xebia.smok.util.HashCodeIdGenerator;
import com.xebia.smok.util.UniqueIdGenerator;
import com.xebia.smok.xml.reader.SmokXmlReader;
import com.xebia.smok.xml.reader.XStreamSmokXmlReader;

public class SmokContainer {

	private static XStreamSmokXmlReader smokXmlReader = new XStreamSmokXmlReader();
	private static ObjectRepository objectRepository = new DiskObjectRepository();
	private static SmokContext smokContext;
	private static UniqueIdGenerator uniqueIdGenerator = new HashCodeIdGenerator();

	public static void initializeContainer(String recordingDirecotry) {
		smokContext = SmokContext.getSmokContext(recordingDirecotry);
	}
	
	public static SmokXmlReader getSmokXmlReader() {
		return smokXmlReader;
	}

	public static ObjectRepository getObjectRepository() {
		return objectRepository;
	}
	
	public static SmokContext getSmokContext() {
		return smokContext;
	}

	public static UniqueIdGenerator getUniqueIdGenerator() {
		return uniqueIdGenerator;
	}

}
