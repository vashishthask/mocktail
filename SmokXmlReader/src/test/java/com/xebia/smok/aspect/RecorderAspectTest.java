package com.xebia.smok.aspect;

import org.junit.Test;

import com.xebia.smok.SmokContext;


public class RecorderAspectTest {

	@Test
	public void shouldDoRecording() throws Exception {
		RecorderAspect recorderAspect = new RecorderAspect();
		SmokContext smokContext = SmokContext.getSmokContext("c:\\sandy");
		smokContext.setRecordingDirectory("recording");
		recorderAspect.doTheRecording("my to be recorded object", "sandy", "ganesh", 12, 23.0);
	}
}
