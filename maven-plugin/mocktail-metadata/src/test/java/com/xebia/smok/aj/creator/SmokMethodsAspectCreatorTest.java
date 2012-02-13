package com.xebia.smok.aj.creator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
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
	File aspectRootDir;
	
	@Before
	public void setup(){
		SmokContainer.initializeContainer("");
		SmokContext smokContext = SmokContext.getSmokContext();
		DirectFieldAccessor dfa = new DirectFieldAccessor(smokContext);
		//Need to set as Smok Context is a singleton class and is getting set-upped from multiple places
		dfa.setPropertyValue("recordingDirectory", "root_dir");
	}
	
	@Test
	public void shouldCreateRecordingMethodAspects() throws Exception {
		final Smok methodSmok = SmokObjectMother.createMethodSmok("AspectedClass", "com.sandy",
				"add", "minus");
		new SmokMethodsAspectCreator(SmokMode.RECORDING_MODE) {
			@Override
			protected void createAspectFile(Smok smok, String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("public aspect AspectAspectedClass"));
				assertThat(templatedMethodObjectString, containsString("String recordingDirectoryPath = \"root_dir\";"));
				assertThat(templatedMethodObjectString, containsString("String fqcn = \"com.sandy.AspectedClass\";"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutadd() : call(* com.sandy.AspectedClass.add(..));"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutminus() : call(* com.sandy.AspectedClass.minus(..));"));
				System.out.println(templatedMethodObjectString);
			}
		}.createAspect(methodSmok, aspectRootDir);
	}

	@Test
	public void shouldCreatePlaybackMethodAspects() throws Exception {
		final Smok methodSmok = SmokObjectMother.createMethodSmok("name","com.xebia",
				"method1", "method2");
		
		new SmokMethodsAspectCreator(SmokMode.PLAYBACK_MODE) {
			@Override
			protected void createAspectFile(Smok smok, String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodSmok.getClassName()));
				assertThat(templatedMethodObjectString, containsString("public aspect Aspectname"));
				assertThat(templatedMethodObjectString, containsString("recordingDirectoryPath = \"root_dir\";"));
				assertThat(templatedMethodObjectString, containsString("String fqcn = \"com.xebia.name\";"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod1() : call(* com.xebia.name.method1(..));"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod2() : call(* com.xebia.name.method2(..));"));
			}
		}.createAspect(methodSmok, aspectRootDir);
	}

}
