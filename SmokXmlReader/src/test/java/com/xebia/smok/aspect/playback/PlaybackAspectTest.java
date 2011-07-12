package com.xebia.smok.aspect.playback;

import static org.junit.Assert.*;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import com.xebia.smok.SmokContainer;


public class PlaybackAspectTest {
	
	DirectFieldAccessor dfa;

	@Before
	public void setup(){
		SmokContainer.initializeContainer("");
		dfa = new DirectFieldAccessor(SmokContainer.getSmokContext());
		dfa.setPropertyValue("recordingDirectory","c:\\sandy\\recording\\test");
	}
	
	
	/*@Test(expected=AssertionFailedError.class)
	public void testPlaybackForNoRecordingDir() throws Exception {
		dfa.setPropertyValue("recordingDirectory","c:\\sandy\\recording\\test\\no_direcotry");
		PlaybackAspect playbackAspect = new PlaybackAspect();
		playbackAspect.playback("sandy", "ganesh", 12, 23.0);
	}

	@Test(expected=AssertionFailedError.class)
	public void testPlaybackForNoRecordings() throws Exception {
		dfa.setPropertyValue("recordingDirectory","c:\\sandy\\recording\\test\\no_recordings.ser");
		PlaybackAspect playbackAspect = new PlaybackAspect();
		playbackAspect.playback("sandy", "ganesh", 12, 23.0);
		
	}*/

	@Test
	public void testPlaybackForRecordings() throws Exception {
		dfa.setPropertyValue("recordingDirectory","c:\\sandy\\recording\\test");
		PlaybackAspect playbackAspect = new PlaybackAspect();
		Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
		assertNotNull(recordedObject);
		assertTrue(recordedObject instanceof String);
		assertEquals("to be recorded object",(String)recordedObject);
		
	}
}
