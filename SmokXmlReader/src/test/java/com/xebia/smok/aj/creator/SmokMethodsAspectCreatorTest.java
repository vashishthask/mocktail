package com.xebia.smok.aj.creator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mock;

import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

public class SmokMethodsAspectCreatorTest {

	@Mock
	File aspectDir;
	
	@Test
	public void shouldCreateRecordingMethodAspects() throws Exception {
		final Smok methodSmok = SmokObjectMother.createMethodSmok("FQCN2",
				"method1", "method2");
		new SmokMethodsAspectCreator(SmokMode.RECORDING_MODE) {
			@Override
			protected void createAspectFile(String fileName, File directory,
					String templatedClassObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedClassObjectString, containsString("method1"));
				assertThat(templatedClassObjectString, containsString("method2"));
				assertThat(templatedClassObjectString, containsString("around"));
			}
		}.createAspect(methodSmok, aspectDir);
	}

	@Test
	public void shouldCreatePlaybackMethodAspects() throws Exception {
		final Smok methodSmok = SmokObjectMother.createMethodSmok("FQCN2",
				"method1", "method2");
		new SmokMethodsAspectCreator(SmokMode.PLAYBACK_MODE) {
			@Override
			protected void createAspectFile(String fileName, File directory,
					String templatedClassObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedClassObjectString, containsString("method1"));
				assertThat(templatedClassObjectString, containsString("method2"));
				assertThat(templatedClassObjectString, containsString("around"));
			}
		}.createAspect(methodSmok, aspectDir);
	}

}
