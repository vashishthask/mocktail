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
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.xebia.smok.SmokContext;
import com.xebia.smok.aj.creator.SmoksAspectsCreator;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;
import com.xebia.smok.xml.reader.XStreamSmokXmlReader;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal smok
 * 
 * @phase process-resources
 */
public class SmokMojo extends AbstractMojo {
	/**
	 * @parameter expression=”${aspectsDirectory}”
	 *            default-value="${target}/aspects"
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
		if (!aspectsDirectory.exists()) {
			aspectsDirectory.mkdirs();
		}
		XStreamSmokXmlReader configReader = new XStreamSmokXmlReader();
		SmokContext.getSmokContext(recordingDir.getAbsolutePath());
		try {
			List<Smok> smoks = configReader.readXml(new FileInputStream(
					configuration));
			System.out.println("\n\n " + smoks + "\n\n");
			SmoksAspectsCreator.ASPECTS_CREATOR.createAspects(smoks,
					aspectsDirectory, SmokMode.RECORDING_MODE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
