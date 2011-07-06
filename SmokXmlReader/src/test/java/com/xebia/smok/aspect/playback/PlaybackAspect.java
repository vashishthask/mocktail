package com.xebia.smok.aspect.playback;

import static junit.framework.Assert.assertFalse;
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
	String fqcn = "com.xebia.smok.aspect.recorder";
	
	public Object playback(Object... paramObjects) {
		// Get the Directory path form SmokContext where we have to get the
		// recording file
		String recordingDirectoryPath = SmokContext.getSmokContext()
				.getRecordingDirectory();
		String fileSeparator = "/";
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replaceAll("\\.", fileSeparator);
		assertTrue("The recordings don't exists " , (new File(recordingDirectoryPath)).exists());
		
		// Create the unique id of param objects to be recorded
		//Look into uniqueness of method
/*		String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName, paramObjects)
				+ "";
*/		String recrodingFileName = uniqueIdGenerator.getUniqueId(paramObjects)
		+ "";

		assertTrue("Recording not in place", objectRepository.objectAlreadyExist(recrodingFileName, recordingDirectoryPath));
		return objectRepository.getObject(recrodingFileName, recordingDirectoryPath);
	}
}
