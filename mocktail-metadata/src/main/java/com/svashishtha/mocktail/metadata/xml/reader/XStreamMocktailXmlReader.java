package com.svashishtha.mocktail.metadata.xml.reader;

import java.io.InputStream;
import java.util.List;

import com.svashishtha.mocktail.metadata.xml.domain.Mocktail;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class XStreamMocktailXmlReader implements MocktailXmlReader {

	@SuppressWarnings("unchecked")
	@Override
	public List<Mocktail> readXml(InputStream sampleXmlStream) {
		XStream xstream = new XStream();
		xstream .addPermission(NoTypePermission.NONE); //forbid everything
		xstream .addPermission(NullPermission.NULL);   // allow "null"
		xstream .addPermission(PrimitiveTypePermission.PRIMITIVES); // allow primitive types
		xstream.allowTypesByWildcard(new String[] { 
		        "com.svashishtha.mocktail.metadata.xml.domain.**",
		        "java.util.**", "java.lang.**"
		        });
		xstream.alias("mocktails", List.class);
		
		xstream.alias("mocktail", Mocktail.class);
		xstream.alias("methods", List.class);
		
		List<Mocktail> mocktails = (List<Mocktail>)xstream.fromXML(sampleXmlStream);
		return mocktails;
	}
}
