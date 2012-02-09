package com.xebia.smok;

import static junit.framework.Assert.assertNotNull;

public class SmokContext {

	private final String recordingDirectory;
	private static SmokContext context;

	private SmokContext(String recordingDirectory) {
		this.recordingDirectory = recordingDirectory;
	}


	public String getRecordingDirectory() {
		return recordingDirectory;
	}
	
	public static SmokContext getSmokContext(String rootDirectory) {
		if (null == context) {
			context = new SmokContext(rootDirectory);
		}
		return context;
	}

	public static SmokContext getSmokContext() {
		assertNotNull(context);
		return context;
	}
}
