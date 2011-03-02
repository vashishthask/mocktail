package com.xebia.smok.aspect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.xebia.smok.SmokContext;
import com.xebia.smok.util.RecordingsRepository;
import com.xebia.smok.util.UniqueIdGenerator;

public aspect RecordingAspect {
	
	pointcut aroundMethodPointcut() : 
		call(* com.xebia.smok.aspect.RecordingAspected.*(..));
	
	
	Object around() : aroundMethodPointcut() {
		// Get the Directory path form SmokContext where we have to store the
		// file
		String recordingDirectoryPath = SmokContext.getSmokContext().getRecordingDirectoryPath();
		
		// Create the unique id of param objects to be recorded
		String recrodingFileName = UniqueIdGenerator.HASH_CODE_IMPL
				.getUniqueId(thisJoinPoint.getArgs()) + "";
		
		//Get the object to be recorded
		Object objectToBeRecorded = proceed();
		
		OutputStream outputStream;
		try {
			File recordingFile = new File(recordingDirectoryPath, recrodingFileName);
			outputStream = new FileOutputStream(recordingFile);
			// Ask Recorder to save the recording file
			RecordingsRepository.SERIALIZER_RECORDINGS_REPOSITORY.marshall(
					objectToBeRecorded, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectToBeRecorded;
	}
}
