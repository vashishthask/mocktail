package com.svashishtha.ws;

import java.io.File;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String className = StringTest.class.getName();
		String methodName = "main";
		String packageName = className.substring(0, className.lastIndexOf("."));
		String location = System.getProperty("user.dir") + File.separator 
				+ "src/test/resources" + File.separator 
				+ packageName.replaceAll("\\.", File.separator);
		System.err.println(location);
		String objectId = className.substring(className.lastIndexOf(".")+1)+"."+methodName;
		System.err.println(objectId);

	}

}
