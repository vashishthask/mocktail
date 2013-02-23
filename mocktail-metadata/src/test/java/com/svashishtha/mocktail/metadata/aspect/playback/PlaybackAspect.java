package com.svashishtha.mocktail.metadata.aspect.playback;

import static junit.framework.Assert.assertTrue;

import java.io.File;


import com.svashishtha.mocktail.metadata.MocktailContainer;
import com.svashishtha.mocktail.metadata.util.UniqueIdGenerator;
import com.svashishtha.mocktail.repository.ObjectRepository;

/**
 * I'll act as a reference template that will be in sync with the playback
 * template
 * 
 */
public class PlaybackAspect {
    ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
    UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getInstance()
            .getUniqueIdGenerator();

    // Populated from fqcn property of mocktail
    // mocktail.getFQCN();
    String fqcn;

    String recordingDirectoryPath = MocktailContainer.getInstance()
            .getRecordingDirectory();

    public Object playback(Object... paramObjects) {

        String fileSeparator = File.separator;

        // Verifying if root recording directory where all recordings exist is
        // already their or not
        assertTrue("The root recordings direcotry doesn't exists "
                + recordingDirectoryPath,
                (new File(recordingDirectoryPath)).exists());

        // Recording directory will also have fqcn
        recordingDirectoryPath = recordingDirectoryPath + fileSeparator
                + fqcn.replace(".", fileSeparator);

        // Verifying if directory where recordings exist is already their or not
        assertTrue("The recordings direcotry don't exists "
                + recordingDirectoryPath,
                (new File(recordingDirectoryPath)).exists());

        // Create the unique id of param objects to be recorded
        // Look into uniqueness of method
        // String recrodingFileName = uniqueIdGenerator.getUniqueId(methodName,
        // paramObjects) + "";

        // Recording file name will be as per the parameters
        String recrodingFileName = uniqueIdGenerator.getUniqueId(paramObjects)
                + "";

        // Verifying if recording is already in place
        assertTrue("Recording not in place " + recrodingFileName,
                objectRepository.objectAlreadyExist(recrodingFileName,
                        recordingDirectoryPath));

        // Returning the recorded api call result
        return objectRepository.getObject(recrodingFileName,
                recordingDirectoryPath);
    }
}
