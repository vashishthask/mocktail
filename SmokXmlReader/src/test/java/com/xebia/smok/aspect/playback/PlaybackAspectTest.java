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
	//Able to playback the api call
	@Test
	public void testPlaybackForRecordings() throws Exception {
		playbackAspect = new PlaybackAspect();
		playbackAspect.fqcn ="com.xebia.smok.aspect.recorder";
		Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
		assertNotNull(recordedObject);
		assertTrue(recordedObject instanceof String);
		assertEquals("to be recorded object",(String)recordedObject);
		
	}
	//Unable to playback the api call when recording directory doesn't exists
	//Unable to playback the api call when recording file doesn't exists
	
	
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

}
