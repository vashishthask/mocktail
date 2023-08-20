package in.malonus.mocktail.metadata;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.malonus.mocktail.MocktailConfig;
import in.malonus.mocktail.repository.ObjectFileOperations;
import in.malonus.mocktail.repository.ObjectRepository;

public class MethodMocktail implements Serializable, AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodMocktail.class);
    private static final long serialVersionUID = 1L;

    private static final String NOT_AVAIL = "?";

    MocktailContainer container = MocktailContainer.getInstance();

    Map<String, Integer> methodCallsMap = new HashMap<String, Integer>();

    private String methodName;

    private String fqcn;

    private String recordingDirectoryPath = "";

    private String recordingBasePath = "";

    private boolean recordingsAvailable;

    public String getFqcn() {
        return fqcn;
    }

    public String getMethodName() {
        return methodName;
    }
    
    public MethodMocktail(Object object) {
        setUp(object);
    }

    private void setUp(Object object) {
        container.setMethodMocktail(this);
        fqcn = object.getClass().getName();
        StackTraceElement element = getStackTraceElement(object.getClass().getName(), Thread.currentThread().getStackTrace());
        methodName = element.getMethodName();
        recordingsAvailable = checkRecordingsOnDisk();
    }

    @Override
    public void close() {
        container.setMethodMocktail(null);
    }

    public boolean isObjectExistOnDisk() {
        return serializedObjectExistsOnDisk();
    }

    private boolean checkRecordingsOnDisk() {
        String recordingPath = System.getProperty("user.dir") + File.separator
                + MocktailConfig.INSTANCE.getRecordingPath() + File.separator
                + fqcn.replace(".", File.separator) + File.separator + methodName;
        LOGGER.debug("The recording path is:"+recordingPath);
        ObjectFileOperations fileOperations = new ObjectFileOperations();
        boolean recAvailable = fileOperations.isRecordingAvailable(recordingPath);
        LOGGER.debug("The recording path is:" + recordingPath + " recordingBasePath:" + recordingBasePath
                + " recAvailable:" + recAvailable + " object details:" + this);

        return recAvailable;
    }

    private boolean serializedObjectExistsOnDisk() {
        if (StringUtils.isNotEmpty(recordingBasePath)) {
            recordingDirectoryPath = recordingBasePath + File.separator;
        }
        recordingDirectoryPath += fqcn.replace(".", File.separator) + File.separator + methodName;

        ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
        return objectRepository.objectAlreadyExist(methodName, recordingDirectoryPath);
    }

    private StackTraceElement getStackTraceElement(String fqcn, StackTraceElement[] stackTrace) {
        if (fqcn == null) {
            return null;
        }
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            if (fqcn.equals(className)) {
                return element;
            } else if (NOT_AVAIL.equals(className)) {
                break;
            }
        }
        return null;
    }

    public void setRecordingBasePath(String recordingBasePath) {
        this.recordingBasePath = recordingBasePath;
    }

    public String getRecordingDirectoryPath() {
        return recordingDirectoryPath;
    }

    public void registerWithMethodCallsMap(String key) {
        if (methodCallsMap.containsKey(key)) {
            Integer numberOfCalls = methodCallsMap.get(key);
            numberOfCalls = new Integer(numberOfCalls.intValue() + 1);
            methodCallsMap.put(key, numberOfCalls);
        } else {
            methodCallsMap.put(key, new Integer(1));
        }
    }

    public int getMethodCalls(String key) {
        if (methodCallsMap.containsKey(key)) {
            return methodCallsMap.get(key).intValue();
        }
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean areRecordingsAvailable() {
        return recordingsAvailable;
    }
}
