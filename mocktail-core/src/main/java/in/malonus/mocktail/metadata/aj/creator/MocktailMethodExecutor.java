package in.malonus.mocktail.metadata.aj.creator;

import java.io.File;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.malonus.mocktail.MocktailMode;
import in.malonus.mocktail.metadata.MethodMocktail;
import in.malonus.mocktail.metadata.MocktailContainer;
import in.malonus.mocktail.repository.ObjectRepository;

public class MocktailMethodExecutor {
    private MocktailContainer mocktailContainer = MocktailContainer.getInstance();
    private ObjectRepository objectRepository = mocktailContainer.getObjectRepository();
    private static final Logger LOGGER = LoggerFactory.getLogger(MocktailMethodExecutor.class);

    public Object executeAspect(ProceedingJoinPoint pjp, String recordingFileName, String recordingBasePath,
            String methodName) throws Throwable {
        LOGGER.debug("EXECUTING ASPECT NOW");

        MethodMocktail methodMocktail = mocktailContainer.getMethodMocktail();

        LOGGER.debug("\nexecuteAspect: The method name is:" + methodName + " methodMocktail is:" + methodMocktail
                + " recordingFileName is:" + recordingFileName);

        if (methodMocktail != null) {
            mocktailContainer.setRecordingDirectory(recordingBasePath);
            LOGGER.debug("\nexecuteAspect: The method name is:" + methodName + " methodMocktail is:" + methodMocktail
                    + " recordingFileName is:" + recordingFileName);
            return mocktailForEachTestMethod(pjp, recordingFileName, isVoidReturnType(pjp), methodMocktail, methodName,
                    recordingBasePath);

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

    private Object mocktailForEachTestMethod(ProceedingJoinPoint pjp, String uniqueCodeForArgs, boolean voidReturnType,
            MethodMocktail testMethodMocktail, String methodToMock, String recordingBasePath) throws Throwable {
        Object objectToBeRecorded = null;
        testMethodMocktail.setRecordingBasePath(recordingBasePath);

        // check if it's recording mode or playback mode

        String fileNameForRecording = methodToMock + "_" + uniqueCodeForArgs;
        String recordingFilePath = getRecordingFilePath(testMethodMocktail, recordingBasePath);

        LOGGER.debug("\nfileNameForRecording is:" + fileNameForRecording);

        boolean objectExistsInRepository = objectRepository.objectAlreadyExist(fileNameForRecording, recordingFilePath);
        LOGGER.debug("\n Object " + fileNameForRecording + " exists? " + objectExistsInRepository);

        if (objectExistsInRepository) {
            
            LOGGER.info("Object exists on cache");
            objectToBeRecorded = objectRepository.getObject(fileNameForRecording, recordingFilePath);
            LOGGER.debug("cached response is:" + objectToBeRecorded);
        } else if (recordingMode()) {
            createDirectoryForFilePath(recordingFilePath);
            objectToBeRecorded = pjp.proceed();
            if (!voidReturnType) {
                LOGGER.info("Object doesn't exists in cache. So recording it.");
                saveRecordingFile(fileNameForRecording, testMethodMocktail, objectToBeRecorded, recordingFilePath);
            }

        }

        LOGGER.debug(" objectToBeRecorded:" + objectToBeRecorded);

        return objectToBeRecorded;
    }

    private boolean recordingMode() {
        MocktailMode mocktailMode = mocktailContainer.getMocktailMode();
        LOGGER.debug("The Mocktail Mode is:" + mocktailMode);

        return (MocktailMode.RECORDING == mocktailMode);
    }

    private void saveRecordingFile(String recordingFileName, MethodMocktail testMethodMocktail,
            Object objectToBeRecorded, String recordingFilePath) {
        LOGGER.debug("MethodMocktail - Recording not already in place so doing the recording:" + recordingFileName + ":"
                + recordingFilePath);
        objectRepository.saveObject(objectToBeRecorded, recordingFileName, recordingFilePath);
        testMethodMocktail.registerWithMethodCallsMap(recordingFileName);
    }

    private void createDirectoryForFilePath(String recordingFilePath) {
        File methodMocktailFile = new File(recordingFilePath);
        if (!methodMocktailFile.exists()) {
            methodMocktailFile.mkdirs();
        }
    }

    private String getRecordingFilePath(MethodMocktail testMethodMocktail, String recordingBasePath) {
        return recordingBasePath + File.separator + testMethodMocktail.getFqcn().replace(".", File.separator)
                + File.separator + testMethodMocktail.getMethodName();
    }

}
