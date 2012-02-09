package com.xebia.smok.aj.creator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.DirectFieldAccessor;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;
import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

public class SmokMethodsAspectCreatorTest {

	@Mock
	File aspectDir;
	
	@Test
	public void shouldCreateRecordingMethodAspects() throws Exception {
		SmokContainer.initializeContainer("");
		SmokContext smokContext = SmokContext.getSmokContext("root_dir");
		DirectFieldAccessor dfa = new DirectFieldAccessor(smokContext);
		//Need to set as Smok Context is a singleton class and is getting set-upped from multiple places
		dfa.setPropertyValue("recordingDirectory", "root_dir");
		final Smok methodSmok = SmokObjectMother.createMethodSmok("FQCN2","com.xebia",
				"method1", "method2");
		new SmokMethodsAspectCreator(SmokMode.RECORDING_MODE) {
			@Override
			protected void createAspectFile(Smok smok, String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("String recordingDirectoryPath = \"root_dir\";"));
				assertThat(templatedMethodObjectString, containsString("String fqcn = \"com.xebia.FQCN2\";"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod1() : call(* com.xebia.FQCN2.method1(..));"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod2() : call(* com.xebia.FQCN2.method2(..));"));
			}
		}.createAspect(methodSmok, aspectDir);
	}

	@Test
	public void shouldCreatePlaybackMethodAspects() throws Exception {
		final Smok methodSmok = SmokObjectMother.createMethodSmok("FQCN2","com.xebia",
				"method1", "method2");
		SmokContainer.initializeContainer("");
		SmokContext smokContext = SmokContext.getSmokContext("root_dir");
		DirectFieldAccessor dfa = new DirectFieldAccessor(smokContext);
		//Need to set as Smok Context is a singleton class and is getting set-upped from multiple places
		dfa.setPropertyValue("recordingDirectory", "root_dir");
		
		new SmokMethodsAspectCreator(SmokMode.PLAYBACK_MODE) {
			@Override
			protected void createAspectFile(Smok smok, String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("recordingDirectoryPath = \"root_dir\";"));
				assertThat(templatedMethodObjectString, containsString("String fqcn = \"com.xebia.FQCN2\";"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod1() : call(* com.xebia.FQCN2.method1(..));"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod2() : call(* com.xebia.FQCN2.method2(..));"));
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("method1"));
				assertThat(templatedMethodObjectString, containsString("method2"));
				assertThat(templatedMethodObjectString, containsString("around"));
				assertThat(templatedMethodObjectString, containsString("I'll represent the playback aspect"));
			}
		}.createAspect(methodSmok, aspectDir);
	}

}
