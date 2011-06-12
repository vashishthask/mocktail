package com.xebia.smok.aspect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.xebia.smok.SmokContext;
import com.xebia.smok.repository.DiskObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

/**
 * I'll represent the recording aspect use me to test the recording aspect whether it is working fine or not and then 
 * update the class and method template files accordingly
 * @author sandeep
 *
 */
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
		
		
		Object objectToBeRecorded = DiskObjectRepository.SERIALIZER_RECORDINGS_REPOSITORY
		.getObject(recrodingFileName, recordingDirectoryPath);
		
		if (null == objectToBeRecorded) {
			System.out.println("Recording not already in place so doing the recording");
			//Get the object to be recorded
			objectToBeRecorded = proceed();
			DiskObjectRepository.SERIALIZER_RECORDINGS_REPOSITORY.saveObject(
					objectToBeRecorded, recrodingFileName,
					recordingDirectoryPath);
		} else {
			System.out.println("Recording already in place so fetching data from recording");
		}
		return objectToBeRecorded;
	}
}
