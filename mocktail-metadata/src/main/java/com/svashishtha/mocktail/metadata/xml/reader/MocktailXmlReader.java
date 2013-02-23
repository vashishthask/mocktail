package com.svashishtha.mocktail.metadata.xml.reader;

import java.io.InputStream;
import java.util.List;

import com.svashishtha.mocktail.metadata.xml.domain.Mocktail;

public interface MocktailXmlReader {

    List<Mocktail> readXml(InputStream sampleXmlStream);

}
