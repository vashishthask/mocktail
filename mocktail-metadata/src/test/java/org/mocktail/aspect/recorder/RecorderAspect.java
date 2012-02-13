package org.mocktail.aspect.recorder;

import static junit.framework.Assert.assertTrue;

import java.io.File;

import org.mocktail.MocktailContainer;
import org.mocktail.MocktailContext;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.util.UniqueIdGenerator;

public class RecorderAspect {

	ObjectRepository objectRepository = MocktailContainer.getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getUniqueIdGenerator();
	
	//Populated from fqcn property of mocktail
	//mocktail.getFQCN();
	String fqcn;

	String recordingDirectoryPath;

	public void doTheRecording(Object objectToBeRecorded,
			Object... paramObjects) {
		
		String fileSeparator = File.separator;

		recordingDirectoryPath = MocktailContext.getMocktailContext().getRecordingDirectory();
		
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
