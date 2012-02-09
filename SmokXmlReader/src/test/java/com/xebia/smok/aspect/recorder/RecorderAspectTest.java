package com.xebia.smok.aspect.recorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import com.xebia.smok.SmokContainer;


public class RecorderAspectTest {

	DirectFieldAccessor dfa;
	private RecorderAspect recorderAspect;

	@Before
	public void setup(){
		SmokContainer.initializeContainer("");
		dfa = new DirectFieldAccessor(SmokContainer.getSmokContext());
	}

	@Test(expected=AssertionFailedError.class)
	public void shouldNotifyRootRecordingDirectoryNotAvailable() throws Exception {
		dfa.setPropertyValue("recordingDirectory","src/test/resources/non_existent_recording_dir");
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn="com.xebia.smok.aspect.recorder";
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
		(new File(recordingDir, "1332060422")).delete();
		recordingDir.delete();
	}
	
	//Should not do the recording if recording already exists
	@Test
	public void shouldExecuteIfRecordingExists() throws Exception {
		String rootRecordingDirectory = "src/test/resources/recording";
		String packagePath = "com.xebia.smok.aspect.recorder";
		File recordingFile = new File(rootRecordingDirectory + File.separator + packagePath.replaceAll("\\.", File.separator) + File.separator + "1332060422");
		
		assertTrue("Recording File doesn't exists", recordingFile.exists());
		
		dfa.setPropertyValue("recordingDirectory",rootRecordingDirectory);
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn=packagePath;
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
	}
	
	@Test
	public void shouldDoRecordingIfRecordingNotTheir() throws Exception {
		String rootRecordingDirectory = "src/test/resources/recording";
		String packagePath = "com.xebia.smok.aspect.recorder.new";
		File recordingFile = new File(rootRecordingDirectory + File.separator + packagePath.replaceAll("\\.", File.separator) + File.separator + "1332060422");
		
		assertFalse("Recording File already exists", recordingFile.exists());
		
		dfa.setPropertyValue("recordingDirectory",rootRecordingDirectory);
		recorderAspect = new RecorderAspect();
		recorderAspect.fqcn=packagePath;
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
		
		assertTrue("Recording File doesn't exists", recordingFile.exists());
		recordingFile.delete();
	}
}
