package com.xebia.smok.aspect.recorder;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;


public class RecorderAspectTest {

	DirectFieldAccessor dfa;

	@Before
	public void setup(){
		dfa = new DirectFieldAccessor(SmokContainer.getSmokContext());
		dfa.setPropertyValue("recordingDirectory","c:\\sandy\\recording\\test");
	}
	
	@Test
	public void shouldDoRecording() throws Exception {
		Assert.assertTrue(true);
		RecorderAspect recorderAspect = new RecorderAspect();
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
	}
}
