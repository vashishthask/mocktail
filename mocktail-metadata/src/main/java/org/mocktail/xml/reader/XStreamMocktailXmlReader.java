package org.mocktail.xml.reader;

import java.io.InputStream;
import java.util.List;

import org.mocktail.xml.domain.Mocktail;

import com.thoughtworks.xstream.XStream;

public class XStreamMocktailXmlReader implements MocktailXmlReader  {

	@SuppressWarnings("unchecked")
	@Override
	public List<Mocktail> readXml(InputStream sampleXmlStream) {
		XStream xstream = new XStream();
		xstream.alias("mocktails", List.class);
		xstream.alias("mocktail", Mocktail.class);
		xstream.alias("methods", List.class);
//		xstream.alias("method", String.class);
		List<Mocktail> mocktails = (List<Mocktail>)xstream.fromXML(sampleXmlStream);
		return mocktails;
	}

}
