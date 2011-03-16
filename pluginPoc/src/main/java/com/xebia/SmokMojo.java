package com.xebia;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.lang.reflect.Field;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.mojo.aspectj.AjcCompileMojo;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal smok
 * 
 * @phase compile
 */
public class SmokMojo extends AjcProperties {
	/**
	 * @parameter expression=”${smok.recording}”
	 * @required
	 */
	private File recordingDirectory;

	public void execute() throws MojoExecutionException {

		AjcCompileMojo ajcCompileMojo = new AjcCompileMojo();
		Class<?> superclass = ajcCompileMojo.getClass().getSuperclass();
		setValue(superclass,ajcCompileMojo, "source",source);
		setValue(superclass,ajcCompileMojo,"target",target);
		setValue(superclass.getSuperclass(),ajcCompileMojo,"project",project);
		setValue(superclass.getSuperclass(),ajcCompileMojo,"basedir",basedir);

		System.out.println("\n\n\n\n ------------------------------- Aspect J -----------------------\n\n\n\n");
		ajcCompileMojo.execute();
		System.out.println("\n\n\n------------------------------- My execution -----------------------\n\n\n");
	}

	@SuppressWarnings("rawtypes")
	public void setValue(Class classToBeSetOn,Object o, String fieldName, Object value)throws RuntimeException {
		Field field;
		try {
			field = classToBeSetOn.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(o, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public File getRecordingDirectory() {
		return recordingDirectory;
	}

	public void setRecordingDirectory(File recordingDirectory) {
		this.recordingDirectory = recordingDirectory;
	}
}
