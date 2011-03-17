package com.xebia.smok;
public aspect RecordingAspect {

	 		pointcut callPointcutb() : 
			call(* com.xebia.smok.aj.creator.TemplateProcesser.b(..));
	
		
		Object around() : callPointcutb() {
			// Get the Directory path form SmokContext where we have to store the
		// file
		String recordingDirectoryPath = "recording_base_directory/a" + File.separator + "com.xebia.smok.aj.creator.TemplateProcesser";
		
		// Create the unique id of param objects to be recorded
		String recrodingFileName = UniqueIdGenerator.HASH_CODE_IMPL
				.getUniqueId(thisJoinPoint.getArgs()) + "";
		
		
		Object objectToBeRecorded = ObjectRepository.SERIALIZER_RECORDINGS_REPOSITORY
		.getObject(recrodingFileName, recordingDirectoryPath);
		
		if (null == objectToBeRecorded) {
			System.out.println("Recording not already in place so doing the recording");
			//Get the object to be recorded
			objectToBeRecorded = proceed();
			ObjectRepository.SERIALIZER_RECORDINGS_REPOSITORY.saveObject(
					objectToBeRecorded, recrodingFileName,
					recordingDirectoryPath);
		} else {
			System.out.println("Recording already in place so fetching data from recording");
		}
		return objectToBeRecorded;
		}	
		
}
