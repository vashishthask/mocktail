package org.mocktail.aspect.recorder;

import java.io.File;


import org.mocktail.MocktailContainer;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.util.UniqueIdGenerator;

/**
 * I'll represent the recording aspect use me to test the recording aspect whether it is working fine or not and then 
 * update the class and method template files accordingly
 * @author sandeep
 *
 */
public aspect RecordingAspect {
	
	ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getInstance().getUniqueIdGenerator();
	String fqcn = "org.mocktail.aspect";
	String recordingDirectoryPath = "c:\\sandy\\recording\\test";

	pointcut aroundMethodPointcut() : call(* org.mocktail.aspect.recorder.RecordingAspected.*(..));
	
	
	Object around() : aroundMethodPointcut() {
		
		String fileSeparator = "/";
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replace(".", fileSeparator);

		if (!(new File(recordingDirectoryPath)).exists()) {
			(new File(recordingDirectoryPath)).mkdirs();
		}
		
		// Create the unique id of param objects to be recorded
		//TODO: Look into method name issue
		/*String recrodingFileName = uniqueIdGenerator.getUniqueId(thisJoinPoint.getStaticPart(), thisJoinPoint.getArgs())
				+ "";*/
		String recrodingFileName = uniqueIdGenerator.getUniqueId(thisJoinPoint.getArgs())
		+ "";
		
		Object objectToBeRecorded = null;
		// Get the object to be recorded
		// Ask Recorder to save the recording file
		if (!objectRepository.objectAlreadyExist(recrodingFileName,
				recordingDirectoryPath)) {
			System.out.println("Recording not already in place so doing the recording");
			objectToBeRecorded = proceed();
			objectRepository.saveObject(objectToBeRecorded, recrodingFileName,
					recordingDirectoryPath);
		} else {
			System.out.println("object already exists so not saving it");
			objectToBeRecorded = objectRepository.getObject(recrodingFileName, recordingDirectoryPath);
		}

		
		return objectToBeRecorded;
	}
}
