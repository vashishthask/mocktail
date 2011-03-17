import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.xebia.smok.SmokContext;
import com.xebia.smok.util.ObjectRepository;
import com.xebia.smok.util.UniqueIdGenerator;

public aspect AspectSmok {

	pointcut callPointcut() : 
		call(* Smok.*(..));
	
	
	Object around() : callPointcut() {
		// Get the Directory path form SmokContext where we have to store the
		// file
		String recordingDirectoryPath = "recording_base_directory/com/xebia/smok/xml/domain" + File.separator + "Smok";
		
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