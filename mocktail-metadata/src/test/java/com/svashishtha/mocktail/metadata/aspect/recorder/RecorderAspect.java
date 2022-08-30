package com.svashishtha.mocktail.metadata.aspect.recorder;

import static org.junit.Assert.assertTrue;

import java.io.File;


import com.svashishtha.mocktail.metadata.MocktailContainer;
import com.svashishtha.mocktail.metadata.util.UniqueIdGenerator;
import com.svashishtha.mocktail.repository.ObjectRepository;

public class RecorderAspect {

    ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
    UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getInstance()
            .getUniqueIdGenerator();
    String fqcn;
    String methodName;

    public void doTheRecording(Object objectToBeRecorded,
            Object... paramObjects) {

        String fileSeparator = File.separator;
        String recordingDirectoryPath = MocktailContainer.getInstance()
                .getRecordingDirectory();

        // Verifying if root recording directory where all recordings exist is
        // already their or not
        assertTrue("The root recordings direcotry doesn't exists "
                + recordingDirectoryPath,
                (new File(recordingDirectoryPath)).exists());

        // Recording directory will also have fqcn
        recordingDirectoryPath = recordingDirectoryPath + fileSeparator
                + fqcn.replace(".", fileSeparator);

        // Verify if recording directory exists or not if doesn't create the
        // directory
        if (!(new File(recordingDirectoryPath)).exists()) {
            (new File(recordingDirectoryPath)).mkdirs();
        }

        // Create the unique id of param objects to be recorded
        // Look into uniqueness of method
        /*
         * String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName,
         * paramObjects) + "";
         */
        // Recording file name will be as per the parameters
        // String methodName = thisJoinPoint.getSignature().getName()
        String recordingFileName = uniqueIdGenerator.getUniqueId(methodName,
                paramObjects) + "";

        // Get the object to be recorded
        // Ask Recorder to save the recording file
        if (!objectRepository.objectAlreadyExist(recordingFileName,
                recordingDirectoryPath)) {
            objectRepository.saveObject(objectToBeRecorded, recordingFileName,
                    recordingDirectoryPath);
        } else {
            System.out.println("object already exists so not saving it");
        }
    }
}
