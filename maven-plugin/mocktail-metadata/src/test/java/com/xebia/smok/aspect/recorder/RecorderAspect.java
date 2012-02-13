package com.xebia.smok.aspect.recorder;

import static junit.framework.Assert.assertTrue;

import java.io.File;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;
import com.xebia.smok.repository.ObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

public class RecorderAspect {

	ObjectRepository objectRepository = SmokContainer.getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = SmokContainer.getUniqueIdGenerator();
	
	//Populated from fqcn property of smok
	//smok.getFQCN();
	String fqcn;

	String recordingDirectoryPath;

	public void doTheRecording(Object objectToBeRecorded,
			Object... paramObjects) {
		
		String fileSeparator = File.separator;

		recordingDirectoryPath = SmokContext.getSmokContext().getRecordingDirectory();
		
		// Verifying if root recording directory where all recordings exist is already their or not
		assertTrue("The root recordings direcotry doesn't exists " + recordingDirectoryPath, (new File(recordingDirectoryPath)).exists());

		
		//Recording directory will also have fqcn
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
						+ fqcn.replaceAll("\\.", fileSeparator);

		//Verify if recording directory exists or not if doesn't create the directory
		if (!(new File(recordingDirectoryPath)).exists()) {
			(new File(recordingDirectoryPath)).mkdirs();
		}

		// Create the unique id of param objects to be recorded
		//Look into uniqueness of method
/*		String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName, paramObjects)
				+ "";*/
		//Recording file name will be as per the parameters
		String recordingFileName = uniqueIdGenerator.getUniqueId(paramObjects)	+ "";

		// Get the object to be recorded
		// Ask Recorder to save the recording file
		if (!objectRepository.objectAlreadyExist(recordingFileName,
				recordingDirectoryPath)) {
			objectRepository.saveObject(objectToBeRecorded, recordingFileName,
					recordingDirectoryPath);
		} else {
			System.out.println("object already exists so not saving it");
		}
	}
}
