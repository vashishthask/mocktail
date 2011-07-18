package com.xebia.smok.aspect.playback;

import static junit.framework.Assert.assertTrue;

import java.io.File;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.repository.ObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

/**
 * I'll act as a reference template that will be in sync with the playback template
 *
 */
public class PlaybackAspect {
	ObjectRepository objectRepository = SmokContainer.getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = SmokContainer.getUniqueIdGenerator();
	String fqcn = "com.xebia.smok.aspect.recorder";
	//	SmokContext.getSmokContext().getRecordingDirectory();
	String recordingDirectoryPath;
	
	public Object playback(Object... paramObjects) {

		String fileSeparator = "/";
		recordingDirectoryPath = "c:\\sandy\\recording\\test";
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replaceAll("\\.", fileSeparator);
		assertTrue("The recordings direcotry don't exists " + recordingDirectoryPath, (new File(recordingDirectoryPath)).exists());
		
		// Create the unique id of param objects to be recorded
		//Look into uniqueness of method
/*		String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName, paramObjects)
				+ "";
*/		String recrodingFileName = uniqueIdGenerator.getUniqueId(paramObjects)
		+ "";

		assertTrue("Recording not in place " + recrodingFileName, objectRepository.objectAlreadyExist(recrodingFileName, recordingDirectoryPath));
		return objectRepository.getObject(recrodingFileName, recordingDirectoryPath);
	}
}