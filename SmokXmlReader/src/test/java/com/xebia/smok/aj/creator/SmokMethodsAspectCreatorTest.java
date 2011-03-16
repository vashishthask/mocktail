package com.xebia.smok.aj.creator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mock;

import com.xebia.smok.SmokContext;
import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

public class SmokMethodsAspectCreatorTest {

	@Mock
	File aspectDir;
	
	@Test
	public void shouldCreateRecordingMethodAspects() throws Exception {
		
		SmokContext.getSmokContext("root_dir");
		final Smok methodSmok = SmokObjectMother.createMethodSmok("FQCN2","com.xebia",
				"method1", "method2");
		new SmokMethodsAspectCreator(SmokMode.RECORDING_MODE) {
			@Override
			protected void createAspectFile(String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("method1"));
				assertThat(templatedMethodObjectString, containsString("method2"));
				assertThat(templatedMethodObjectString, containsString("around"));
				assertThat(templatedMethodObjectString, containsString("String recordingDirectoryPath = \"root_dir/com/xebia\" + File.separator + \"FQCN2\";"));
			}
		}.createAspect(methodSmok, aspectDir);
	}

	/*@Test
	public void shouldCreatePlaybackMethodAspects() throws Exception {
		final Smok methodSmok = SmokObjectMother.createMethodSmok("FQCN2",
				"method1", "method2");
		new SmokMethodsAspectCreator(SmokMode.PLAYBACK_MODE) {
			@Override
			protected void createAspectFile(String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("method1"));
				assertThat(templatedMethodObjectString, containsString("method2"));
				assertThat(templatedMethodObjectString, containsString("around"));
				assertThat(templatedMethodObjectString, containsString("I'll skip the actual execution"));
			}
		}.createAspect(methodSmok, aspectDir);
	}*/

}
