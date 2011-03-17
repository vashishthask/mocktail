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

public class SmokClassAspectCreatorTest {

	@Mock
	File aspectDir;
	
	@Test
	public void shouldCreateRecordingAspectForClass() throws Exception {
		SmokContext.getSmokContext("root_dir");
		final Smok classSmok = SmokObjectMother.createClassSmok("FQCN", "com.xebia");
		new SmokClassAspectCreator(SmokMode.RECORDING_MODE) {
			@Override
			protected void createAspectFile(Smok smok, String fileName, File directory,
					String templatedClassObjectString) throws IOException {
				assertThat(fileName, is(classSmok.getClassName()));
				assertThat(templatedClassObjectString, containsString(classSmok.getClassName()));
				assertThat(templatedClassObjectString, containsString("String recordingDirectoryPath = \"root_dir/com/xebia\" + File.separator + \"FQCN\";"));
			}
		}.createAspect(classSmok, aspectDir);
	}

	/*@Test
	public void shouldCreatePlaybackAspectForClass() throws Exception {
		final Smok classSmok = SmokObjectMother.createClassSmok("FQCN");
		new SmokClassAspectCreator(SmokMode.PLAYBACK_MODE) {
			@Override
			protected void createAspectFile(String fileName, File directory,
					String templatedClassObjectString) throws IOException {
				assertThat(fileName, is(classSmok.getClassName()));
				assertThat(templatedClassObjectString, containsString(classSmok.getClassName()));
				assertThat(templatedClassObjectString, containsString("I'll skip the actual execution"));
			}
		}.createAspect(classSmok, aspectDir);
	}*/
}
