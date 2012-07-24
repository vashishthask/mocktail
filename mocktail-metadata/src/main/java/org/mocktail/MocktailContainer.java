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
    private MethodMocktail methodMocktail;
    
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
    
    public UniqueIdGenerator getUniqueIdGenerator() {
        return uniqueIdGenerator;
    }

    public String getRecordingDirectory() {
        return recordingDirectory;
    }

    public void setRecordingDirectory(String directoryPath) {
        if (directoryPath.contains("\\")) {
            this.recordingDirectory = directoryPath.replace("\\", "\\\\");
        } else {
            this.recordingDirectory = directoryPath;
        }
    }
    
    public void clean(){
        this.recordingDirectory = null;
        this.init();
    }

    public void setMethodMocktail(MethodMocktail methodMocktail) {
        this.methodMocktail = methodMocktail;
    }
    
    public void resetMethodMocktail() {
        methodMocktail = null;
    }
    
    public MethodMocktail getMethodMocktail() {
        return methodMocktail;
    }
}
