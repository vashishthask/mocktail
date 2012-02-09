package com.xebia.smok.aspect.playback;

import static org.junit.Assert.*;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;


public class PlaybackAspectTest {
	
	DirectFieldAccessor dfa;
	private PlaybackAspect playbackAspect;

	@Before
	public void setup(){
		SmokContainer.initializeContainer("");
		SmokContext smokContext = SmokContext.getSmokContext("");
		dfa = new DirectFieldAccessor(SmokContainer.getSmokContext());
		dfa.setPropertyValue("recordingDirectory","src/test/resources/recording");
	}

	@Test
	public void testPlaybackForRecordings() throws Exception {
		playbackAspect = new PlaybackAspect();
		playbackAspect.fqcn ="com.xebia.smok.aspect.recorder";
		Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
		assertNotNull(recordedObject);
		assertTrue(recordedObject instanceof String);
		assertEquals("to be recorded object",(String)recordedObject);
		
	}

	@Test(expected=AssertionFailedError.class)
	public void testPlaybackForInvalidRecordingDir() throws Exception {
		dfa.setPropertyValue("recordingDirectory","src/test/resources/non_existent_recording_dir");
		playbackAspect = new PlaybackAspect();
		playbackAspect.fqcn ="com.xebia.smok.aspect.recorder";
		Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
		assertNull(recordedObject);
	}
	
	@Test(expected=AssertionFailedError.class)
	public void testPlaybackForInvalideFQCN() throws Exception {
		dfa.setPropertyValue("recordingDirectory","src/test/resources/recording");
		playbackAspect = new PlaybackAspect();
		playbackAspect.fqcn ="invalid_fqcn";
		Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
		assertNull(recordedObject);
	}

	@Test(expected=AssertionFailedError.class)
	public void testPlaybackForRecordingNotAvailable() throws Exception {
		playbackAspect = new PlaybackAspect();
		playbackAspect.fqcn ="com.xebia.smok.aspect.recorder";
		Object recordedObject = playbackAspect.playback("sandh", "ganesh", 12, 23.0);
		assertNull(recordedObject);
	}

}
