package org.mocktail.aj.creator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mocktail.MocktailContainer;
import org.mocktail.MocktailContext;
import org.mocktail.MocktailObjectMother;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;
import org.springframework.beans.DirectFieldAccessor;

public class MocktailMethodsAspectCreatorTest {

	@Mock
	File aspectRootDir;
	
	@Before
	public void setup(){
		MocktailContainer.initializeContainer("");
		MocktailContext mocktailContext = MocktailContext.getMocktailContext();
		DirectFieldAccessor dfa = new DirectFieldAccessor(mocktailContext);
		//Need to set as Mocktail Context is a singleton class and is getting set-upped from multiple places
		dfa.setPropertyValue("recordingDirectory", "root_dir");
	}
	
	@Test
	public void shouldCreateRecordingMethodAspects() throws Exception {
		final Mocktail methodMocktail = MocktailObjectMother.createMethodMocktail("name","org.mocktail",
				"method1", "method2");
		new MocktailMethodsAspectCreator(MocktailMode.RECORDING_MODE) {
			@Override
			protected void createAspectFile(Mocktail mocktail, String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodMocktail.getClassName()));
				assertThat(templatedMethodObjectString, containsString("public aspect RecorderAspectname"));
				assertThat(templatedMethodObjectString, containsString("String recordingDirectoryPath = \"root_dir\";"));
				assertThat(templatedMethodObjectString, containsString("String fqcn = \"org.mocktail.name\";"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod1() : call(* org.mocktail.name.method1(..));"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod2() : call(* org.mocktail.name.method2(..));"));
			}
		}.createAspect(methodMocktail, aspectRootDir);
	}

	@Test
	public void shouldCreatePlaybackMethodAspects() throws Exception {
		final Mocktail methodMocktail = MocktailObjectMother.createMethodMocktail("name","org.mocktail",
				"method1", "method2");
		
		new MocktailMethodsAspectCreator(MocktailMode.PLAYBACK_MODE) {
			@Override
			protected void createAspectFile(Mocktail mocktail, String fileName, File directory,
					String templatedMethodObjectString) throws IOException {
				assertThat(fileName, is(methodMocktail.getClassName()));
				assertThat(templatedMethodObjectString, containsString("public aspect PlaybackAspectname"));
				assertThat(templatedMethodObjectString, containsString("recordingDirectoryPath = \"root_dir\";"));
				assertThat(templatedMethodObjectString, containsString("String fqcn = \"org.mocktail.name\";"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod1() : call(* org.mocktail.name.method1(..));"));
				assertThat(templatedMethodObjectString, containsString("pointcut callPointcutmethod2() : call(* org.mocktail.name.method2(..));"));
			}
		}.createAspect(methodMocktail, aspectRootDir);
	}

}
