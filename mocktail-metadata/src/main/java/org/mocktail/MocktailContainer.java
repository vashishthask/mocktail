package org.mocktail;

import org.mocktail.repository.DiskObjectRepository;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.util.HashCodeIdGenerator;
import org.mocktail.util.UniqueIdGenerator;
import org.mocktail.xml.reader.MocktailXmlReader;
import org.mocktail.xml.reader.XStreamMocktailXmlReader;

public class MocktailContainer {

    private XStreamMocktailXmlReader mocktailXmlReader;
    private ObjectRepository objectRepository;
    private UniqueIdGenerator uniqueIdGenerator;
    private String recordingDirectory;
    
    private static MocktailContainer mocktailContainer = new MocktailContainer();

    private MocktailContainer() {
        init();
    }

    private void init() {
        mocktailXmlReader = new XStreamMocktailXmlReader();
        objectRepository = new DiskObjectRepository();
        uniqueIdGenerator = new HashCodeIdGenerator();
    }
    
    public static MocktailContainer getInstance(){
        return mocktailContainer;
    }

    public MocktailXmlReader getMocktailXmlReader() {
        return mocktailXmlReader;
    }

    public ObjectRepository getObjectRepository() {
        return objectRepository;
    }
    
    public void initRecordingDirectory(String directoryPath){
        if (directoryPath.contains("\\")) {
            this.setRecordingDirectory(directoryPath.replace("\\", "\\\\"));
        } else {
            this.setRecordingDirectory(directoryPath);
        }
    }

    public UniqueIdGenerator getUniqueIdGenerator() {
        return uniqueIdGenerator;
    }

    public String getRecordingDirectory() {
        return recordingDirectory;
    }

    private void setRecordingDirectory(String recordingDirectory) {
        this.recordingDirectory = recordingDirectory;
    }
    
    public void clean(){
        this.recordingDirectory = null;
        this.init();
    }
}
