package in.malonus.maven.plugin.mocktail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.utils.io.FileUtils;
import org.codehaus.mojo.aspectj.AjcCompileMojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.malonus.mocktail.MocktailMode;
import in.malonus.mocktail.metadata.MocktailContainer;
import in.malonus.mocktail.metadata.aj.creator.MocktailAspectsCreator;
import in.malonus.mocktail.metadata.xml.domain.Mocktail;
import in.malonus.mocktail.metadata.xml.reader.XStreamMocktailXmlReader;

/**
 * Goal which touches a timestamp file.
 * 
 *
 */
@Mojo(name="mocktail", defaultPhase = LifecyclePhase.VALIDATE)
public class MocktailMojo extends AjcCompileMojo {
    private static final Logger LOGGER = LoggerFactory.getLogger(MocktailMojo.class);

    
    @Parameter(property="aspectsDirectory", defaultValue = "target/generated/aspects")
    private File aspectsDirectory;


    @Parameter(property="mocktailconfig", defaultValue = "src/test/resources/mocktail.xml")
    private File configuration;

    
    @Parameter(property="recordingDir", defaultValue = "src/test/resources/recordings")
    private File recordingDir;

    
    @Parameter(property="mode", defaultValue = "recording")
    private String mode;

    public void execute() throws MojoExecutionException {

        LOGGER.debug("Executing the mocktail mojo");
        if (!aspectsDirectory.exists()) {
            aspectsDirectory.mkdirs();
        }
        
        MocktailContainer mocktailContainer = MocktailContainer.getInstance();
        mocktailContainer.setRecordingDirectory(recordingDir.getAbsolutePath());
        LOGGER.debug("MocktailMojo: The Mocktail mode is:" + mocktailContainer.getMocktailMode());
        System.err.println("MocktailMojo: The Mocktail mode is:" + mode);

        try {
            XStreamMocktailXmlReader configReader = new XStreamMocktailXmlReader();
            List<Mocktail> mocktails = configReader.readXml(new FileInputStream(configuration));

            // TODO:A hack for time being as we will be either generating
            // recording/playback aspects at a time
            MocktailAspectsCreator mocktailAspectsCreator = new MocktailAspectsCreator();
            if (isPlaybackMode()) {
                mocktailAspectsCreator.createPlaybackAspects(mocktails, aspectsDirectory);
            } else if (isRecordingNewMode()) {
                cleanRecordings();
                mocktailAspectsCreator.createRecordingAspects(mocktails, aspectsDirectory);
            } else {
                mocktailAspectsCreator.createRecordingAspects(mocktails, aspectsDirectory);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private void cleanRecordings() {
        try {
            System.err.println("Cleaning recordingDir:"+recordingDir.getAbsolutePath());
            if (recordingDir.exists())
                FileUtils.cleanDirectory(recordingDir);
        } catch (IOException e) {
            LOGGER.error("Could not clean recording dir with path:" + recordingDir, e.getMessage());
            new IllegalArgumentException("Could not clean recording dir with path:" + recordingDir, e);
        }
    }

    private boolean isRecordingNewMode() {
        return mode.equalsIgnoreCase(MocktailMode.RECORDING_NEW.toString());
    }

    private boolean isPlaybackMode() {
        return mode.equalsIgnoreCase(MocktailMode.PLAYBACK.toString());
    }

    public void setValue(Class<?> classToBeSetOn, Object o, String fieldName, Object value) {
        try {
            Field field = classToBeSetOn.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, value);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
