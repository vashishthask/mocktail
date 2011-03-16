package com.xebia.smok;

import static junit.framework.Assert.assertNotNull;

import java.io.File;

public class SmokContext {

	private final String rootDirectory;
	private static SmokContext context;

	private SmokContext(String rotDirectory) {
		this.rootDirectory = rotDirectory;
	}


	public String getRootDirectory() {
		return rootDirectory;
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
