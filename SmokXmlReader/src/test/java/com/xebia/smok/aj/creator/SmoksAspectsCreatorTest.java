package com.xebia.smok.aj.creator;

import static junit.framework.Assert.assertTrue;

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
		String testAspectsDirectory = getAbsolutePath("src", "test",
				"resources", "com", "xebia", "smok", "aspectsDirectory");
		SmoksAspectsCreator.ASPECTS_CREATOR.createAspects(SmokObjectMother
				.getSmoksForAspects(), new File(testAspectsDirectory),
				SmokMode.RECORDING_MODE);
		String firstSmokFile = getAbsolutePath("src", "test",
				"resources", "com", "xebia", "smok", "aspectsDirectory","com","xebia","smok","aj","creator","AspectTemplateProcesser.aj");
		assertTrue(new File(firstSmokFile).isFile());

		String secondSmokFile = getAbsolutePath("src", "test",
				"resources", "com", "xebia", "smok", "aspectsDirectory","com","xebia","smok","xml","domain","AspectSmok.aj");
		assertTrue(new File(secondSmokFile).isFile());
		//Uncomment the deleteDir if you want to see the aspects
		deleteDir(new File(testAspectsDirectory));
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();

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
