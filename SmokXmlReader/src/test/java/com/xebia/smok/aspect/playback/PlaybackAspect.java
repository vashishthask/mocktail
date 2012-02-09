package com.xebia.smok.aspect.playback;

import static junit.framework.Assert.assertTrue;

import java.io.File;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;
import com.xebia.smok.repository.ObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

/**
 * I'll act as a reference template that will be in sync with the playback template
 *
 */
public class PlaybackAspect {
	ObjectRepository objectRepository = SmokContainer.getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = SmokContainer.getUniqueIdGenerator();
	
	//Populated from fqcn property of smok
	//smok.getFQCN();
	String fqcn;
	
	// Populated from smok context recording directory
	// SmokContext.getSmokContext().getRecordingDirectory();
	String recordingDirectoryPath = SmokContext.getSmokContext().getRecordingDirectory();
	
	public Object playback(Object... paramObjects) {

		String fileSeparator = File.separator;
		
		//Recording directory will also have fqcn
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replaceAll("\\.", fileSeparator);

		// Verifying if directory where recordings exist is already their or not
		assertTrue("The recordings direcotry don't exists " + recordingDirectoryPath, (new File(recordingDirectoryPath)).exists());
		
		// Create the unique id of param objects to be recorded
		//Look into uniqueness of method
//		String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName, paramObjects) + "";
		
		//Recording file name will be as per the parameters
		String recrodingFileName = uniqueIdGenerator.getUniqueId(paramObjects) + "";

		//Verifying if recording is already in place 
		assertTrue("Recording not in place " + recrodingFileName, objectRepository.objectAlreadyExist(recrodingFileName, recordingDirectoryPath));
		
		//Returning the recorded api call result 
		return objectRepository.getObject(recrodingFileName, recordingDirectoryPath);
	}
}
