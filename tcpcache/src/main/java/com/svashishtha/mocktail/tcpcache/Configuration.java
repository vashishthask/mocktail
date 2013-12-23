package com.svashishtha.mocktail.tcpcache;

import com.svashishtha.mocktail.MocktailConfig;
import com.svashishtha.mocktail.MocktailMode;

public class Configuration {
    
    public final static String DEFAULT_LOCAL_PORT = "8080";
    public final static String DEFAULT_REMOTE_HOST = "127.0.0.1";
    public final static String DEFAULT_REMOTE_PORT = "80";
    public static final String DEFAULT_CACHE_LOCATION = "src/test/resources";
    private int localPort;
    private String targetHost;
    private int targetPort;
    private String testClassName;
    private String testMethodName;
    private MocktailMode mocktailMode;
    
    public boolean isCachingOn() {
        String cachingIndStr = MocktailConfig.INSTANCE.getProperty("cachingOn");
        if ("false".equals(cachingIndStr)) {
            return false;
        }
        return true;
    }
    
    public Configuration(int localPort, String targetHost, int targetPort, Class<?> testClass,
            String testMethodName, MocktailMode mocktailMode){
        this.localPort = localPort;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.testClassName = testClass.getName();
        this.testMethodName = testMethodName;
        this.mocktailMode = mocktailMode;
        
    }

    public int getLocalPort() {
        return localPort;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public MocktailMode getMocktailMode() {
        return mocktailMode;
    }
    
    public String getCacheLoation(){
        String cacheLocation = MocktailConfig.INSTANCE.getProperty("recordingDir");
        if(cacheLocation == null || "".equals(cacheLocation)){
            cacheLocation = DEFAULT_CACHE_LOCATION;
        }
        return cacheLocation;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }
}
