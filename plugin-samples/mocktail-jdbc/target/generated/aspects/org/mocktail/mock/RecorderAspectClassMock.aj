import static junit.framework.Assert.assertTrue;

import java.io.File;

import org.mocktail.MocktailContainer;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.util.UniqueIdGenerator;

public aspect RecorderAspectClassMock {

	ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
	UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getInstance().getUniqueIdGenerator();
	
	pointcut callPointcut() : call(* org.mocktail.mock.ClassMock.*(..));
	
	
	Object around() : callPointcut() {
		
		String fqcn = "org.mocktail.mock.ClassMock";
		String fileSeparator = File.separator;
		
		String recordingDirectoryPath = "/Users/shrikant/code/github/mocktail/plugin-samples/mocktail-jdbc/target/generated/recordings" + fileSeparator + fqcn.replaceAll("\\.", fileSeparator);

		// Verifying if root recording directory where all recordings exist is already their or not

		if (!(new File(recordingDirectoryPath)).exists()) {
			(new File(recordingDirectoryPath)).mkdirs();
		}
		
		String methodName = thisJoinPoint.getSignature().getName();
		// Create the unique id of param objects to be recorded
		String recordingFileName = uniqueIdGenerator.getUniqueId(methodName, thisJoinPoint.getArgs())	+ "";		
		
		
		Object objectToBeRecorded = null;
		// Get the object to be recorded
		// Ask Recorder to save the recording file
		if (!objectRepository.objectAlreadyExist(recordingFileName,
				recordingDirectoryPath)) {
			System.out.println("Recording not already in place so doing the recording");
			objectToBeRecorded = proceed();
			objectRepository.saveObject(objectToBeRecorded, recordingFileName,
					recordingDirectoryPath);
		} else {
			System.out.println("object already exists so not saving it");
			objectToBeRecorded = objectRepository.getObject(recordingFileName, recordingDirectoryPath);
		}
		
		return objectToBeRecorded;
	}
}
