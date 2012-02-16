package org.mocktail.aspect.recorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.mocktail.MocktailContainer;
import org.springframework.beans.DirectFieldAccessor;


public class RecorderAspectTest {

	DirectFieldAccessor dfa;
	private RecorderAspect recorderAspect;

	@Before
	public void setup(){
		MocktailContainer.initializeContainer("");
		dfa = new DirectFieldAccessor(MocktailContainer.getMocktailContext());
	}

	@Test(expected=AssertionFailedError.class)
	public void shouldNotifyRootRecordingDirectoryNotAvailable() throws Exception {
		dfa.setPropertyValue("recordingDirectory","src/test/resources/non_existent_recording_dir");
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn="org.mocktail.aspect.recorder";
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
		
	}
	
	@Test
	public void shouldCreateRecordingDirectory() throws Exception {
		String rootRecordingDirectory = "src/test/resources/recording";
		String packagePath = "new";
		File recordingDir = new File(rootRecordingDirectory + File.separator + packagePath.replaceAll("\\.", File.separator));
		
		assertFalse("Recording directory already exists", recordingDir.exists());
		
		dfa.setPropertyValue("recordingDirectory",rootRecordingDirectory);
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn=packagePath;
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);

		assertTrue("Recording directory doesn't exists", recordingDir.exists());
		(new File(recordingDir, "1359766052")).delete();
		recordingDir.delete();
	}
	
	//Should not do the recording if recording already exists
	@Test
	public void shouldExecuteIfRecordingExists() throws Exception {
		String rootRecordingDirectory = "src/test/resources/recording";
		String packagePath = "org.mocktail.aspect.recorder";
		File recordingFile = new File(rootRecordingDirectory + File.separator + packagePath.replaceAll("\\.", File.separator) + File.separator + "1359766052");
		
		assertTrue("Recording File doesn't exists", recordingFile.exists());
		
		dfa.setPropertyValue("recordingDirectory",rootRecordingDirectory);
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn=packagePath;
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
	}
	
	@Test
	public void shouldDoRecordingIfRecordingNotTheir() throws Exception {
		String rootRecordingDirectory = "src/test/resources/recording";
		String packagePath = "org.mocktail.aspect.recorder.new";
		File recordingFile = new File(rootRecordingDirectory + File.separator + packagePath.replaceAll("\\.", File.separator) + File.separator + "1359766052");
		
		assertFalse("Recording File already exists", recordingFile.exists());
		
		dfa.setPropertyValue("recordingDirectory",rootRecordingDirectory);
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn=packagePath;
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
		
		assertTrue("Recording File doesn't exists", recordingFile.exists());
		recordingFile.delete();
	}
}
