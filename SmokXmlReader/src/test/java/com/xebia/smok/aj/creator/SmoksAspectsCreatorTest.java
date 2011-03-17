package com.xebia.smok.aj.creator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.xebia.smok.SmokContext;
import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.SmokMode;


public class SmoksAspectsCreatorTest {

	@Test
	public void testCreateAspects() throws Exception {
		SmokContext.getSmokContext("recording_base_directory");
		String testAspectsDirectory = getAbsolutePath("src", "test", "resources",
				"com", "xebia", "smok", "aspectsDirectory");
		SmoksAspectsCreator.ASPECTS_CREATOR.createAspects(SmokObjectMother.getSmoksForAspects(), new File(testAspectsDirectory), SmokMode.RECORDING_MODE);
	}
	
	private String getAbsolutePath(String... path) throws IOException {
		File file = new File(".");
		StringBuffer absolutePath = new StringBuffer(file.getCanonicalPath());
		for (String pathElement : path) {
			absolutePath.append(File.separator).append(pathElement);
		}
		return absolutePath.toString();
	}
}
