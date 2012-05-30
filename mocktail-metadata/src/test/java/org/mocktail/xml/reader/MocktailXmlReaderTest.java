package org.mocktail.xml.reader;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mocktail.xml.domain.Mocktail;

public class MocktailXmlReaderTest {

	MocktailXmlReader mocktailXmlReader;

	@Before
	public void setUp() {
		mocktailXmlReader = new XStreamMocktailXmlReader();
	}

	@Test
	public void shouldReadXml() {
		InputStream sampleXmlStream = this.getClass().getClassLoader()
				.getResourceAsStream("mocktailsSample.xml");
		List<Mocktail> mocktails = mocktailXmlReader.readXml(sampleXmlStream);
		
		assertEquals("Size of mocktails didnt matched", mocktails.size(), 2);
		assertThat(mocktails.get(0).getClassName(), is("className"));
		assertThat(mocktails.get(0).getClassPackageName(), is("classPackage"));
		assertThat(mocktails.get(0).getMethods().size(), is(2));
		assertThat(mocktails.get(0).getMethods().get(0), is("method1"));
		assertThat(mocktails.get(0).getMethods().get(1), is("method2"));
		assertThat(mocktails.get(0).onlyForClass(), is(false));

		assertThat(mocktails.get(1).getClassName(), is("fqcn2"));
		assertNull(mocktails.get(1).getMethods());
		assertThat(mocktails.get(1).onlyForClass(), is(true));
	}

}
