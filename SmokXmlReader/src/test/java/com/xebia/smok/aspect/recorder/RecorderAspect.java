package com.xebia.smok.aspect.recorder;

import java.io.File;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;
import com.xebia.smok.repository.ObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

public class RecorderAspect {

	ObjectRepository objectRepository = SmokContainer.getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = SmokContainer.getUniqueIdGenerator();
	String fqcn = "com.xebia.smok.aspect.recorder";
	

	public void doTheRecording(Object objectToBeRecorded,
			Object... paramObjects) {
		
//		String methodName = "doTheRecording";
		// Get the Directory path form SmokContext where we have to store the
		// file
		String recordingDirectoryPath = SmokContext.getSmokContext()
				.getRecordingDirectory();
		String fileSeparator = "/";
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replaceAll("\\.", fileSeparator);

		if (!(new File(recordingDirectoryPath)).exists()) {
			(new File(recordingDirectoryPath)).mkdirs();
		}

		// Create the unique id of param objects to be recorded
		//Look into uniqueness of method
/*		String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName, paramObjects)
				+ "";
*/		String recrodingFileName = uniqueIdGenerator.getUniqueId(paramObjects)
		+ "";

		// Get the object to be recorded
		// Ask Recorder to save the recording file
		if (!objectRepository.objectAlreadyExist(recrodingFileName,
				recordingDirectoryPath)) {
			objectRepository.saveObject(objectToBeRecorded, recrodingFileName,
					recordingDirectoryPath);
		} else {
			System.out.println("object already exists so not saving it");
		}
	}
}
