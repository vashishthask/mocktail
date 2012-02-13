package org.mocktail.xml.reader;

import java.io.InputStream;
import java.util.List;

import org.mocktail.xml.domain.Mocktail;

public interface MocktailXmlReader {

	List<Mocktail> readXml(InputStream sampleXmlStream);
	
}
