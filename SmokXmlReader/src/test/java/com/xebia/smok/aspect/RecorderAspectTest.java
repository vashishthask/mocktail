package com.xebia.smok.aspect;

import junit.framework.Assert;

import org.junit.Test;

import com.xebia.smok.SmokContext;


public class RecorderAspectTest {

	@Test
	public void shouldDoRecording() throws Exception {
		Assert.assertTrue(true);
		RecorderAspect recorderAspect = new RecorderAspect();
		SmokContext.getSmokContext("c:\\sandy\\recording\\test");
		recorderAspect.doTheRecording("to be recorded object", "sandy", "ganesh", 12, 23.0);
	}
}
