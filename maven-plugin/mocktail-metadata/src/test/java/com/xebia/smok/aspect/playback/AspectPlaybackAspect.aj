package com.xebia.smok.aspect.playback;
import static junit.framework.Assert.assertTrue;

import java.io.File;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokContext;
import com.xebia.smok.repository.ObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

//TODO Will be removed
/**
 * I'll represent the playback aspect use me to test the playback aspect whether it is working fine or not and then 
 * update the class and method template files accordingly
 */
public aspect AspectPlaybackAspect {
	ObjectRepository objectRepository = SmokContainer.getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = SmokContainer.getUniqueIdGenerator();
	String fqcn = "com.xebia.smok.aspect.playback";
	//SmokContext.getSmokContext().getRecordingDirectory();	
	String recordingDirectoryPath;
	
	pointcut aroundMethodPointcut() : 
		call(* com.xebia.smok.aspect.playback.PlaybackAspected.*(..));
	
	Object around() : aroundMethodPointcut() {
		String fileSeparator = "/";
		recordingDirectoryPath = "c:\\sandy\\recording\\test";
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replaceAll("\\.", fileSeparator);
		assertTrue("The recordings directory don't exists " + recordingDirectoryPath, (new File(recordingDirectoryPath)).exists());
		System.out.println("Doing addition");
		// Create the unique id of param objects to be recorded
		//Look into uniqueness of method
/*		String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName, paramObjects)
				+ "";
*/		String recrodingFileName = uniqueIdGenerator.getUniqueId(thisJoinPoint.getArgs())
		+ "";

		assertTrue("Recording not in place " + recrodingFileName, objectRepository.objectAlreadyExist(recrodingFileName, recordingDirectoryPath));
		return objectRepository.getObject(recrodingFileName, recordingDirectoryPath);
	}
}