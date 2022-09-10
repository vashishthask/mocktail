package in.malonus.tcpcache;

import java.util.Properties;

import org.slf4j.LoggerFactory;

public class Configuration {
    
    public static final String CACHEMODE = "cachemode";
    public final static String DEFAULT_LOCAL_PORT = "8080";
    public final static String DEFAULT_REMOTE_HOST = "127.0.0.1";
    public final static String DEFAULT_REMOTE_PORT = "80";
    public static final String DEFAULT_CACHE_LOCATION = "src/test/resources";
    private static Configuration configuration = new Configuration();
    private Properties config = new Properties();
    private final org.slf4j.Logger log = LoggerFactory.getLogger(Configuration.class);
    private int localPort;
    private String targetHost;
    private int targetPort;
    private String testClassName;
    private String testMethodName;
    
    public static Configuration getInstance() {
        return configuration ;
    }
    
    public boolean isCachingOn() {
        String cachingIndStr = config.getProperty("cachingOn");
        if ("false".equals(cachingIndStr)) {
            return false;
        }
        return true;
    }
    
    public String getProperty(String key) {
        return config.getProperty(key);
    }
    
    public Configuration(int localPort, String targetHost, int targetPort, Class<?> testClass,
            String testMethodName){
        this.localPort = localPort;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.testClassName = testClass.getName();
        this.testMethodName = testMethodName;
    }

    private Configuration() {
        try {
            config.load(this.getClass().getClassLoader().getResourceAsStream("tcpcache.properties"));
        } catch (Exception e) {
            log.error("MocktailConfig could not be loaded from classpath:" + e.getClass().getName() + ":message:"
                    + e.getMessage());
        }
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

    public CacheMode getCacheMode() {
        return getMode(config.getProperty(CACHEMODE));
    }
    
    private CacheMode getMode(String mode) {
        if (mode.equalsIgnoreCase(CacheMode.PLAYBACK.toString()))
            return CacheMode.PLAYBACK;
        else if (mode.equalsIgnoreCase(CacheMode.RECORDING.toString())) {
            return CacheMode.RECORDING;
        } else if (mode.equalsIgnoreCase(CacheMode.RECORDING_NEW.toString())) {
            return CacheMode.RECORDING_NEW;
        }
        return CacheMode.RECORDING;
    }
    
    public String getCacheLoation(){
        String cacheLocation = config.getProperty("recordingDir");
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
