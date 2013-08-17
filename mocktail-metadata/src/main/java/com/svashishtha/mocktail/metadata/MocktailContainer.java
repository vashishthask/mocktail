package com.svashishtha.mocktail.metadata;

import com.svashishtha.mocktail.metadata.util.HashCodeIdGenerator;
import com.svashishtha.mocktail.metadata.util.UniqueIdGenerator;
import com.svashishtha.mocktail.metadata.xml.reader.MocktailXmlReader;
import com.svashishtha.mocktail.metadata.xml.reader.XStreamMocktailXmlReader;
import com.svashishtha.mocktail.repository.ObjectRepository;

public class MocktailContainer {

    private XStreamMocktailXmlReader mocktailXmlReader;
    private UniqueIdGenerator uniqueIdGenerator;
    private String recordingDirectory;
    private MethodMocktail methodMocktail;
    private ObjectRepository objectRepository;
    
    
    private static MocktailContainer mocktailContainer = new MocktailContainer();

    private MocktailContainer() {
        init();
    }

    private void init() {
        mocktailXmlReader = new XStreamMocktailXmlReader();
        uniqueIdGenerator = new HashCodeIdGenerator();
        objectRepository = ObjectRepositoryFactory.create("yaml");
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
