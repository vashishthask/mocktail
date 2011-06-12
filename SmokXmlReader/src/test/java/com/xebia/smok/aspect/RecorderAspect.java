package com.xebia.smok.aspect;

import java.io.File;

import com.xebia.smok.SmokContext;
import com.xebia.smok.repository.DiskObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

public class RecorderAspect {

	public void doTheRecording(Object objectToBeRecorded,
			Object... paramObjects) {

		// Get the Directory path form SmokContext where we have to store the
		// file
		String recordingDirectoryPath = SmokContext.getSmokContext()
				.getRootDirectory();
		if(!(new File(recordingDirectoryPath)).exists()) {
			(new File(recordingDirectoryPath)).mkdirs();
		}

		// Create the unique id of param objects to be recorded
		String recrodingFileName = UniqueIdGenerator.HASH_CODE_IMPL
				.getUniqueId(paramObjects) + "";

		// Get the object to be recorded
		// Ask Recorder to save the recording file
		if (null == DiskObjectRepository.SERIALIZER_RECORDINGS_REPOSITORY
				.getObject(recrodingFileName, recordingDirectoryPath)) {
			DiskObjectRepository.SERIALIZER_RECORDINGS_REPOSITORY.saveObject(
					objectToBeRecorded, recrodingFileName,
					recordingDirectoryPath);
		}
	}
}
