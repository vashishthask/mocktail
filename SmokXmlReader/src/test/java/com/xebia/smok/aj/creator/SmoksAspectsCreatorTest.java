package com.xebia.smok.aj.creator;

import static com.xebia.FileTestUtil.absolutePath;
import static com.xebia.FileTestUtil.deleteDir;
import static junit.framework.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.xebia.smok.SmokContext;
import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.SmokMode;

public class SmoksAspectsCreatorTest {

	@Test
	public void testCreateAspects() throws Exception {
		SmokContext.getSmokContext("recording_base_directory");
		String testAspectsDirectory = absolutePath("src", "test", "resources",
				"com", "xebia", "smok", "aspectsDirectory");
		SmoksAspectsCreator.ASPECTS_CREATOR.createAspects(SmokObjectMother
				.getSmoksForAspects(), new File(testAspectsDirectory),
				SmokMode.RECORDING_MODE);

		String firstSmokFile = absolutePath("src", "test", "resources", "com",
				"xebia", "smok", "aspectsDirectory", "com", "xebia", "smok",
				"aj", "creator", "AspectTemplateProcesser.aj");
		assertTrue(new File(firstSmokFile).isFile());

		String secondSmokFile = absolutePath("src", "test", "resources", "com",
				"xebia", "smok", "aspectsDirectory", "com", "xebia", "smok",
				"xml", "domain", "AspectSmok.aj");
		assertTrue(new File(secondSmokFile).isFile());
		// Uncomment the deleteDir if you want to see the aspects
		deleteDir(new File(testAspectsDirectory));
	}

}
