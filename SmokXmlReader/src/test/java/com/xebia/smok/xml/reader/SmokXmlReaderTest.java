package com.xebia.smok.xml.reader;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.xebia.smok.xml.domain.Smok;

public class SmokXmlReaderTest {

	SmokXmlReader smokXmlReader;

	@Before
	public void setUp() {
		smokXmlReader = new XStreamSmokXmlReader();
	}

	@Test
	public void shouldReadXml() {
		InputStream sampleXmlStream = this.getClass().getClassLoader()
				.getResourceAsStream("smoksSample.xml");
		List<Smok> smoks = smokXmlReader.readXml(sampleXmlStream);
		
		assertEquals("Size of smoks didnt matched", smoks.size(), 2);
		assertThat(smoks.get(0).getClassName(), is("className"));
		assertThat(smoks.get(0).getClassPackageName(), is("classPackage"));
		assertThat(smoks.get(0).getMethods().size(), is(2));

		assertThat(smoks.get(1).getClassName(), is("fqcn2"));
		assertNull(smoks.get(1).getMethods());
	}

}
