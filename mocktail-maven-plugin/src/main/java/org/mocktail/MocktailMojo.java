package org.mocktail;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.mojo.aspectj.AjcCompileMojo;
import org.mocktail.aj.creator.MocktailAspectsCreator;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;
import org.mocktail.xml.reader.XStreamMocktailXmlReader;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal mocktail
 * 
 * @phase validate
 */
public class MocktailMojo extends AjcCompileMojo {
    /**
     * @parameter expression="${aspectsDirectory}"
     *            default-value="${target}/generated/aspects"
     * @required
     */
    private File aspectsDirectory;

    /**
     * @parameter expression="${mocktailconfig}" default-value="mocktail.xml"
     * @required
     */
    private File configuration;

    /**
     * @parameter expression="${recordingDir}" default-value="src/recording"
     * @required
     */
    private File recordingDir;

    /**
     * @parameter expression="${mode}" default-value="recording"
     * @required
     */
    private String mode;

    public void execute() throws MojoExecutionException {

        System.out.println("Executing the mocktail mojo");
        if (!aspectsDirectory.exists()) {
            aspectsDirectory.mkdirs();
        }
        XStreamMocktailXmlReader configReader = new XStreamMocktailXmlReader();
//        MocktailContainer.initializeContainer(recordingDir.getAbsolutePath());
        MocktailContainer container = MocktailContainer.getInstance();
        container.initRecordingDirectory(recordingDir.getAbsolutePath());
        try {
            List<Mocktail> mocktails = configReader
                    .readXml(new FileInputStream(configuration));
            System.out.println("\n\n " + mocktails + "\n\n");
            // TODO:A hack for time being as we will be either generating
            // recording/playback aspects at a time
            if (mode.equalsIgnoreCase(MocktailMode.RECORDING_MODE
                    .getModeDirectory())) {
                MocktailAspectsCreator.ASPECTS_CREATOR.createAspects(mocktails,
                        aspectsDirectory, MocktailMode.RECORDING_MODE);
            } else {
                MocktailAspectsCreator.ASPECTS_CREATOR.createAspects(mocktails,
                        aspectsDirectory, MocktailMode.PLAYBACK_MODE);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setValue(Class<?> classToBeSetOn, Object o, String fieldName,
            Object value) {
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
