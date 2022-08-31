package com.svashishtha.mocktail.metadata.aj.creator;

import java.io.File;

import org.aspectj.lang.ProceedingJoinPoint;

import com.svashishtha.mocktail.metadata.MethodMocktail;
import com.svashishtha.mocktail.metadata.MocktailContainer;
import com.svashishtha.mocktail.repository.ObjectRepository;

public class MocktailMethodExecutor {
    private ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();

	
	public Object mocktailForEachTestMethod(ProceedingJoinPoint pjp,
            String uniqueCodeForArgs, boolean voidReturnType,
            MethodMocktail testMethodMocktail, String methodToMock,String recordingBasePath) throws Throwable {
        Object objectToBeRecorded = null;
        testMethodMocktail.setRecordingBasePath(recordingBasePath);
        
        // check if it's recording mode or playback mode

        String fileNameForRecording = methodToMock + "_" + uniqueCodeForArgs;
        String recordingFilePath = getRecordingFilePath(testMethodMocktail, recordingBasePath);
        
        System.err.println("\nfileNameForRecording is:"+fileNameForRecording);
        
        boolean objectExistsInRepository = objectRepository
                .objectAlreadyExist(fileNameForRecording, recordingFilePath);
        System.out.println("\n object "+ fileNameForRecording +" exists? "
        		+ objectExistsInRepository);

        boolean recordingMode = !(testMethodMocktail.isPlaybackMode());

        if (objectExistsInRepository) {
            
            objectToBeRecorded = objectRepository.getObject(
            		fileNameForRecording, recordingFilePath);
            System.out.println("MethodMocktail:"+ "cached response is:"+objectToBeRecorded);
        } else if (recordingMode){
            createDirectoryForFilePath(recordingFilePath);
            objectToBeRecorded = pjp.proceed();
            if (!voidReturnType) {
                saveRecordingFile(fileNameForRecording, testMethodMocktail, objectToBeRecorded, recordingFilePath);
            }
            
        }
        
        System.out
                    .println(" objectToBeRecorded:"+objectToBeRecorded);

        
        return objectToBeRecorded;
    }

	private void saveRecordingFile(String recordingFileName, MethodMocktail testMethodMocktail,
			Object objectToBeRecorded, String recordingFilePath) {
		System.out
		        .println("MethodMocktail - Recording not already in place so doing the recording:"
		                + recordingFileName
		                + ":"
		                + recordingFilePath);
		objectRepository.saveObject(objectToBeRecorded,
		        recordingFileName, recordingFilePath);
		testMethodMocktail
		.registerWithMethodCallsMap(recordingFileName);
	}

	private void createDirectoryForFilePath(String recordingFilePath) {
		File methodMocktailFile = new File(recordingFilePath);
		if (!methodMocktailFile.exists()) {
		    methodMocktailFile.mkdirs();
		}
	}

	private String getRecordingFilePath(MethodMocktail testMethodMocktail, String recordingBasePath) {
		return recordingBasePath + File.separator
                + testMethodMocktail.getFqcn().replace(".", File.separator)
                + File.separator + testMethodMocktail.getMethodName();
	}

}
