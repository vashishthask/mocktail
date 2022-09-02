package com.svashishtha.mocktail.metadata;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.svashishtha.mocktail.MocktailConfig;
import com.svashishtha.mocktail.repository.ObjectFileOperations;
import com.svashishtha.mocktail.repository.ObjectRepository;

public class MethodMocktail implements Serializable{
    private static final long serialVersionUID = 1L;

    private static final String NOT_AVAIL = "?";

    MocktailContainer container = MocktailContainer.getInstance();
    
    Map<String, Integer> methodCallsMap = new HashMap<String, Integer>();

    private String methodName;

    private String fqcn;

    private String recordingDirectoryPath = "";

    private String recordingBasePath = "";

    public String getFqcn() {
        return fqcn;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setUp(Object object) {
        container.setMethodMocktail(this);
        StackTraceElement element = null;
        fqcn = object.getClass().getName();
        element = getStackTraceElement(this.getClass().getName(), Thread.currentThread()
                .getStackTrace());
        methodName = element.getMethodName();
    }



    public void close() {
        container.resetMethodMocktail();
//        if(!serializedObjectExistsOnDisk()){
//            saveSerializedIndicatorOnDisk();
//        }
        System.out.println("Calling MethodMocktail.close() method");
    }
    
    public boolean isObjectExistOnDisk(){
        return serializedObjectExistsOnDisk();
    }
    
    
    
    
    private boolean checkRecordingsOnDisk() {
    	String  recordingPath = System.getProperty("user.dir") + File.separator 
    			+ MocktailConfig.INSTANCE.getProperty("recordingDir") + File.separator
    			+ fqcn.replace(".", File.separator) + File.separator+methodName;
    	ObjectFileOperations fileOperations = new ObjectFileOperations();
    	boolean recAvailable = fileOperations.isRecordingAvailable(recordingPath);
    	System.err.println("The recording path is:"+recordingPath+" recordingBasePath:"
    	+recordingBasePath+ " recAvailable:"+recAvailable + " object details:"+this);

		return recAvailable;
    }
    
    
    private boolean serializedObjectExistsOnDisk() {
        if(StringUtils.isNotEmpty(recordingBasePath)){
            recordingDirectoryPath = recordingBasePath + File.separator;
        }
        recordingDirectoryPath += fqcn.replace(".", File.separator) + File.separator+methodName;

        ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
        return objectRepository.objectAlreadyExist(methodName,
                recordingDirectoryPath);
    }

    /*private void saveSerializedIndicatorOnDisk() {
        System.out.println("The recording directory path is:"+recordingDirectoryPath);
        if (!(new File(recordingDirectoryPath)).exists()) {
            boolean directoryCreated = (new File(recordingDirectoryPath)).mkdirs();
            System.out.println("Directory Created:"+directoryCreated);
        }
        String recordingFileName = methodName;
        
        ObjectRepository objectRepository = MocktailContainer.getInstance().getObjectRepository();
        objectRepository.saveObject(methodName, recordingFileName,
                recordingDirectoryPath);
    }*/

    private StackTraceElement getStackTraceElement(String fqcn,
            StackTraceElement[] stackTrace) {
        if (fqcn == null) {
            return null;
        }
        boolean next = false;
        for (StackTraceElement element : stackTrace) {
            if (next) {
                return element;
            }
            String className = element.getClassName();
            if (fqcn.equals(className)) {
                next = true;
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
        if(methodCallsMap.containsKey(key)){
            Integer numberOfCalls = methodCallsMap.get(key);
            numberOfCalls = new Integer(numberOfCalls.intValue()+1);
            methodCallsMap.put(key, numberOfCalls);
        } else{
            methodCallsMap.put(key, new Integer(1));
        }
    }
    
    public int getMethodCalls(String key) {
        if(methodCallsMap.containsKey(key)){
            return methodCallsMap.get(key).intValue();
        }
        return 0;
    }
    
    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

	public boolean areRecordingsAvailable() {
		return checkRecordingsOnDisk();
	}
}
