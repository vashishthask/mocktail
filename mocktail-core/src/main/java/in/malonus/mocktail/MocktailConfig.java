package in.malonus.mocktail;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.LoggerFactory;

public enum MocktailConfig {
    INSTANCE();

    private static final String DEFAULT_RECORDING_PATH = "src/test/resources/recordings";

    Properties config = new Properties();
    private final org.slf4j.Logger log = LoggerFactory.getLogger(MocktailConfig.class);

    private MocktailConfig() {
        try {
            InputStream configStream = this.getClass().getClassLoader()
                    .getResourceAsStream("mocktailconfig.properties");
            if (configStream != null)
                config.load(configStream);
        } catch (Exception e) {
            log.error("MocktailConfig could not be loaded from classpath:" + e.getClass().getName() + ":message:"
                    + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public String getRecordingPath() {
        String recordingDirPath = config.getProperty("recodingDir");
        if (recordingDirPath != null)
            return recordingDirPath;
        return DEFAULT_RECORDING_PATH;
    }
}
