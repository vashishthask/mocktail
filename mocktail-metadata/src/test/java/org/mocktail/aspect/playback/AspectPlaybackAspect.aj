package org.mocktail.aspect.playback;
import static junit.framework.Assert.assertTrue;


import java.io.File;

import org.mocktail.MocktailContainer;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.util.UniqueIdGenerator;

//TODO Will be removed
/**
 * I'll represent the playback aspect use me to test the playback aspect whether it is working fine or not and then 
 * update the class and method template files accordingly
 */
public aspect AspectPlaybackAspect {
	ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getInstance().getUniqueIdGenerator();
	String fqcn = "org.mocktail.aspect.playback";
	String recordingDirectoryPath;
	
	pointcut aroundMethodPointcut() : 
		call(* org.mocktail.aspect.playback.PlaybackAspected.*(..));
	
	Object around() : aroundMethodPointcut() {
		String fileSeparator = "/";
		recordingDirectoryPath = "c:\\sandy\\recording\\test";
		recordingDirectoryPath = recordingDirectoryPath + fileSeparator
				+ fqcn.replace(".", fileSeparator);
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