package in.malonus.mocktail.metadata;

import in.malonus.mocktail.MocktailMode;
import in.malonus.mocktail.metadata.repository.YamlDiskRepository;
import in.malonus.mocktail.metadata.util.HashCodeIdGenerator;
import in.malonus.mocktail.metadata.util.UniqueIdGenerator;
import in.malonus.mocktail.metadata.xml.reader.MocktailXmlReader;
import in.malonus.mocktail.metadata.xml.reader.XStreamMocktailXmlReader;
import in.malonus.mocktail.repository.ObjectRepository;

public class MocktailContainer {

    private XStreamMocktailXmlReader mocktailXmlReader;
    private UniqueIdGenerator uniqueIdGenerator;
    private String recordingDirectory;
    private MethodMocktail methodMocktail;
    private ObjectRepository objectRepository;
    private MocktailMode mocktailMode;

    private static MocktailContainer mocktailContainer = new MocktailContainer();

    private MocktailContainer() {
        init();
    }

    private void init() {
        mocktailXmlReader = new XStreamMocktailXmlReader();
        uniqueIdGenerator = new HashCodeIdGenerator();
        objectRepository = ObjectRepositoryFactory.create("yaml");
    }

    public static MocktailContainer getInstance() {
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

    public void clean() {
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

    public void setMocktailMode(String mode) {
        this.mocktailMode = getMode(mode);

    }

    private MocktailMode getMode(String mode) {
        if (mode.equalsIgnoreCase(MocktailMode.PLAYBACK.getModeDirectory()))
            return MocktailMode.PLAYBACK;
        else if (mode.equalsIgnoreCase(MocktailMode.RECORDING.getModeDirectory())) {
            return MocktailMode.RECORDING;
        } else if (mode.equalsIgnoreCase(MocktailMode.RECORDING_NEW.getModeDirectory())) {
            return MocktailMode.RECORDING_NEW;
        }
        return MocktailMode.RECORDING;
    }

    public MocktailMode getMocktailMode() {
        return mocktailMode;
    }
}
