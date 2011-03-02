package com.xebia.smok;

import static junit.framework.Assert.assertNotNull;

import java.io.File;

public class SmokContext {

	private final String rotDirectory;
	private String recordingDirectory;
	private static SmokContext context;

	private SmokContext(String rotDirectory) {
		this.rotDirectory = rotDirectory;
	}

	public void setRecordingDirectory(String recordingDirectory) {
		this.recordingDirectory = recordingDirectory;
	}

	public String getRecordingDirectoryPath() {
		return rotDirectory + File.separator + recordingDirectory;
	}

	public static SmokContext getSmokContext(String rotDirectory) {
		if (null == context) {
			context = new SmokContext(rotDirectory);
		}
		return context;
	}

	public static SmokContext getSmokContext() {
		assertNotNull(context);
		return context;
	}
}
