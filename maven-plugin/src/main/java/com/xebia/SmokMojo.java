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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.mojo.aspectj.AjcCompileMojo;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.aj.creator.SmoksAspectsCreator;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;
import com.xebia.smok.xml.reader.XStreamSmokXmlReader;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal smok
 * 
 * @phase process-classes
 */
public class SmokMojo extends AjcCompileMojo {
	/**
	 * @parameter expression=”${aspectsDirectory}”
	 *            default-value="${target}/generated/aspects"
	 * @required
	 */
	private File aspectsDirectory;

	/**
	 * @parameter expression=”${smokconfig}” default-value="smok.xml"
	 * @required
	 */
	private File configuration;

	/**
	 * @parameter expression=”${recordingDir}” default-value="src/recording"
	 * @required
	 */
	private File recordingDir;

	public void execute() throws MojoExecutionException {

		System.out.println("Executing the smok mojo");
		if (!aspectsDirectory.exists()) {
			aspectsDirectory.mkdirs();
		}
		XStreamSmokXmlReader configReader = new XStreamSmokXmlReader();
		SmokContainer.initializeContainer("c:");
		// SmokContext.getSmokContext(recordingDir.getAbsolutePath());
		try {
			List<Smok> smoks = configReader.readXml(new FileInputStream(
					configuration));
			System.out.println("\n\n " + smoks + "\n\n");
			SmoksAspectsCreator.ASPECTS_CREATOR.createAspects(smoks,
					aspectsDirectory, SmokMode.RECORDING_MODE);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Generated aspect files at " + aspectsDirectory.getAbsolutePath());
		AjcCompileMojo ajcCompileMojo = new AjcCompileMojo();
		Class<?> superclass = ajcCompileMojo.getClass().getSuperclass();
		setValue(superclass, ajcCompileMojo, "source", source);
		setValue(superclass, ajcCompileMojo, "target", target);
		setValue(superclass, ajcCompileMojo, "aspectDirectory", aspectsDirectory.getAbsolutePath());
		setValue(superclass.getSuperclass(), ajcCompileMojo, "project", project);
		setValue(superclass.getSuperclass(), ajcCompileMojo, "basedir", basedir);
		
		System.out.println("************************************" );
		System.out.println("************************************" );
		System.out.println("Executing ajc mojo" );
		ajcCompileMojo.execute();
		System.out.println("************************************" );
		System.out.println("************************************" );

	}

	@SuppressWarnings("rawtypes")
	public void setValue(Class classToBeSetOn, Object o, String fieldName,
			Object value) throws RuntimeException {
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
}
