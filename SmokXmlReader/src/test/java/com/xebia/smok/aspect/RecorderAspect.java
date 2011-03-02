package com.xebia.smok.aspect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.xebia.smok.SmokContext;
import com.xebia.smok.util.RecordigsRepository;
import com.xebia.smok.util.UniqueIdGenerator;

public class RecorderAspect {

	public void doTheRecording(Object objectToBeRecorded, 
			Object... paramObjects) {
		
		// Get the Directory path form SmokContext where we have to store the
		// file
		String recordingDirectoryPath = SmokContext.getSmokContext().getRecordingDirectoryPath();

		// Create the unique id of param objects to be recorded
		String recrodingFileName = UniqueIdGenerator.HASH_CODE_IMPL
				.getUniqueId(paramObjects) + "";
		OutputStream outputStream;
		try {
			File recordingFile = new File(recordingDirectoryPath, recrodingFileName);
			outputStream = new FileOutputStream(recordingFile);
			// Ask Recorder to save the recording file
			RecordigsRepository.SERIALIZER_RECORDINGS_REPOSITORY.marshall(
					objectToBeRecorded, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
