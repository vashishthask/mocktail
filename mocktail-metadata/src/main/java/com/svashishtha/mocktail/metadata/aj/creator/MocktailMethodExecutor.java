package com.svashishtha.mocktail.metadata.aj.creator;

import java.io.File;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.svashishtha.mocktail.MocktailMode;
import com.svashishtha.mocktail.metadata.MethodMocktail;
import com.svashishtha.mocktail.metadata.MocktailContainer;
import com.svashishtha.mocktail.repository.ObjectRepository;

public class MocktailMethodExecutor {
    private MocktailContainer mocktailContainer = MocktailContainer.getInstance();
	private ObjectRepository objectRepository = mocktailContainer.getObjectRepository();

	
    public Object executeAspect(ProceedingJoinPoint pjp,
            String recordingFileName, String recordingBasePath, String methodName) throws Throwable {
        System.out.println("EXECUTING ASPECT NOW");
        System.out.println("++++++++++++++++++++");

        MethodMocktail methodMocktail = mocktailContainer.getMethodMocktail();
        
        if (methodMocktail != null) {
        System.err.println("\nexecuteAspect: The method name is:"+methodName+ " methodMocktail is:"+methodMocktail
            + " recordingFileName is:"+recordingFileName); 
            return mocktailForEachTestMethod(pjp,
                    recordingFileName, isVoidReturnType(pjp), methodMocktail,
                    methodName, recordingBasePath);
                    
        } 
        
        return pjp.proceed();
    }

    private boolean isVoidReturnType(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        boolean voidReturnType = false;
        if (method.getReturnType().equals(Void.TYPE)) {
            voidReturnType = true;
        }
        return voidReturnType;
    }
    
    private Object mocktailForEachTestMethod(ProceedingJoinPoint pjp,
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

        if (objectExistsInRepository) {
            
            objectToBeRecorded = objectRepository.getObject(
            		fileNameForRecording, recordingFilePath);
            System.out.println("MethodMocktail:"+ "cached response is:"+objectToBeRecorded);
        } else if (recordingMode()){
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

	private boolean recordingMode() {
		MocktailMode mocktailMode = mocktailContainer.getMocktailMode();
		System.err.println("The Mocktail Mode is:"+mocktailMode);

		return (MocktailMode.RECORDING == mocktailMode);
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
