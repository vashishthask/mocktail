package com.xebia.smok.xml.reader;

import java.io.InputStream;
import java.util.List;

import com.xebia.smok.xml.domain.Smok;

public interface SmokXmlReader {

	List<Smok> readXml(InputStream sampleXmlStream);

}
