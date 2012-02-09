package com.xebia.smok.xml.reader;

import java.io.InputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.xebia.smok.xml.domain.Smok;

public class XStreamSmokXmlReader implements SmokXmlReader  {

	@SuppressWarnings("unchecked")
	@Override
	public List<Smok> readXml(InputStream sampleXmlStream) {
		XStream xstream = new XStream();
		xstream.alias("smoks", List.class);
		xstream.alias("smok", Smok.class);
		xstream.alias("methods", List.class);
		xstream.alias("method", String.class);
		List<Smok> smoks = (List<Smok>)xstream.fromXML(sampleXmlStream);
		return smoks;
	}

}
