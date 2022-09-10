package in.malonus.mocktail.metadata.aj.creator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.DirectFieldAccessor;

import in.malonus.mocktail.MocktailMode;
import in.malonus.mocktail.metadata.MocktailContainer;
import in.malonus.mocktail.metadata.MocktailObjectMother;
import in.malonus.mocktail.metadata.aj.creator.MocktailAspectsCreator;
import in.malonus.mocktail.metadata.aj.creator.MocktailTemplateProcesser;
import in.malonus.mocktail.metadata.xml.domain.AspectType;
import in.malonus.mocktail.metadata.xml.domain.Mocktail;

public class MocktailTemplateProcesserTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MocktailTemplateProcesserTest.class);

    @Before
    public void setupMocktailContainer() {
        MocktailContainer mocktailContainer = MocktailContainer.getInstance();
        mocktailContainer.clean();
        DirectFieldAccessor dfa = new DirectFieldAccessor(mocktailContainer);
        // Need to set as Mocktail Context is a singleton class and is getting
        // set-upped from multiple places
        dfa.setPropertyValue("recordingDirectory", "root_dir");
    }

    @Test
    public void shouldCreateClassAspect() throws Exception {
        Mocktail mocktail = createClassMocktail();
        MocktailTemplateProcesser templateProcessor = new MocktailTemplateProcesser();
        String templatedString = templateProcessor.processTemplate(mocktail, AspectType.CLASS_ASPECT_TYPE,
                MocktailMode.RECORDING);

        String expectedpointcut = "pointcut callPointcut() : call(* in.malonus.mocktail.metadata.aj.creator.TemplateProcesserTest.*(..));";
        assertTrue(templatedString.contains(expectedpointcut));
        assertTrue(templatedString
                .contains("String fqcn = \"in.malonus.mocktail.metadata.aj.creator.TemplateProcesserTest\";"));
    }

    @Test
    public void shouldCreateMethodAspect() throws Exception {

        Mocktail mocktail = createMethodMocktail();

        MocktailTemplateProcesser templateProcessor = new MocktailTemplateProcesser();

        String templatedString = templateProcessor.processTemplate(mocktail, AspectType.METHODS_ASPECT_TYPE,
                MocktailMode.RECORDING);
        LOGGER.debug(templatedString);

        String expectedpointcut = "@Around(\"execution(* in.malonus.mocktail.metadata.aj.creator.TemplateProcesserTest.method1(..))\")";
        assertTrue(templatedString.contains(expectedpointcut));

        expectedpointcut = "@Around(\"execution(* in.malonus.mocktail.metadata.aj.creator.TemplateProcesserTest.method2(..))\")";
        assertTrue(templatedString.contains(expectedpointcut));
        assertTrue(
                templatedString.contains("fqcn = \"in.malonus.mocktail.metadata.aj.creator.TemplateProcesserTest\";"));
    }

    private Mocktail createMethodMocktail() {
        Mocktail mocktail = new Mocktail();
        mocktail.setClassName("TemplateProcesserTest");
        mocktail.setClassPackageName("in.malonus.mocktail.metadata.aj.creator");
        List<String> methods = new ArrayList<String>();
        methods.add("method1");
        methods.add("method2");
        mocktail.setMethods(methods);
        return mocktail;
    }

    private Mocktail createClassMocktail() {
        Mocktail mocktail = new Mocktail();
        mocktail.setClassName("TemplateProcesserTest");
        mocktail.setClassPackageName("in.malonus.mocktail.metadata.aj.creator");
        return mocktail;
    }

    @Rule
    public TemporaryFolder temporaryAspectDir = new TemporaryFolder();

    @Test
    public void testAspectFileCreation() throws Exception {
        MocktailContainer.getInstance().clean();

        MocktailAspectsCreator aspectsCreator = new MocktailAspectsCreator();
        File aspectsRootDirectory = temporaryAspectDir.newFolder("aspect");
        boolean aspectFileExists = new File(aspectsRootDirectory, "RecorderAspectAspectedClass.aj").exists();
        assertFalse("Aspect already exists", aspectFileExists);

        aspectsCreator.createClassAspect(MocktailObjectMother.createClassMocktail("AspectedClass", ""),
                aspectsRootDirectory, MocktailMode.RECORDING);

        LOGGER.debug("aspect root directory:" + aspectsRootDirectory.getAbsolutePath());

        assertTrue("Aspect doesn't exists",
                (new File(aspectsRootDirectory, "RecorderAspectAspectedClass.java")).exists());
    }

    @Test
    @Ignore
    public void shouldCreateRecordingMethodAspects() throws Exception {
        setupMocktailContainer();
        final Mocktail methodMocktail = MocktailObjectMother.createMethodMocktail("name",
                "in.malonus.mocktail.metadata", "method1", "method2");

        MocktailTemplateProcesser templateProcessor = new MocktailTemplateProcesser();
        String templatedMethodObjectString = templateProcessor.processTemplate(methodMocktail,
                AspectType.METHODS_ASPECT_TYPE, MocktailMode.RECORDING);

        assertThat(templatedMethodObjectString, containsString("public aspect RecorderAspectname"));
        assertThat(templatedMethodObjectString, containsString("String recordingDirectoryPath = \"root_dir\";"));
        assertThat(templatedMethodObjectString, containsString("String fqcn = \"in.malonus.mocktail.metadata.name\";"));
        assertThat(templatedMethodObjectString, containsString(
                "pointcut callPointcutmethod1() : call(* in.malonus.mocktail.metadata.name.method1(..));"));
        assertThat(templatedMethodObjectString, containsString(
                "pointcut callPointcutmethod2() : call(* in.malonus.mocktail.metadata.name.method2(..));"));

    }

    @Test
    @Ignore
    public void shouldCreatePlaybackMethodAspects() throws Exception {
        final Mocktail methodMocktail = MocktailObjectMother.createMethodMocktail("name",
                "in.malonus.mocktail.metadata", "method1", "method2");

        MocktailTemplateProcesser templateProcessor = new MocktailTemplateProcesser();
        String templatedMethodObjectString = templateProcessor.processTemplate(methodMocktail,
                AspectType.METHODS_ASPECT_TYPE, MocktailMode.PLAYBACK);
        LOGGER.debug("The templatemethod string is\n" + templatedMethodObjectString);
        assertThat(templatedMethodObjectString, containsString("public class PlaybackAspectname"));
        assertThat(templatedMethodObjectString, containsString("recordingDirectoryPath = \"root_dir\";"));
        assertThat(templatedMethodObjectString, containsString("String fqcn = \"in.malonus.mocktail.metadata.name\";"));
        assertThat(templatedMethodObjectString, containsString(
                "pointcut callPointcutmethod1() : call(* in.malonus.mocktail.metadata.name.method1(..));"));
        assertThat(templatedMethodObjectString, containsString(
                "pointcut callPointcutmethod2() : call(* in.malonus.mocktail.metadata.name.method2(..));"));
    }

    @Test
    public void shouldCreateRecordingAspectForClass() throws Exception {

        final Mocktail classMocktail = MocktailObjectMother.createClassMocktail("AspectedClass", "com.sandy");

        MocktailTemplateProcesser templateProcessor = new MocktailTemplateProcesser();

        String templatedClassObjectString = templateProcessor.processTemplate(classMocktail,
                AspectType.CLASS_ASPECT_TYPE, MocktailMode.RECORDING);
        LOGGER.debug(templatedClassObjectString);
        assertThat(templatedClassObjectString, containsString("public aspect RecorderAspectAspectedClass"));
        assertThat(templatedClassObjectString, containsString("String fqcn = \"com.sandy.AspectedClass\";"));
        assertThat(templatedClassObjectString, containsString("recordingDirectoryPath = \"root_dir\""));
        assertThat(templatedClassObjectString,
                containsString("pointcut callPointcut() : call(* com.sandy.AspectedClass.*(..));"));
        LOGGER.debug(templatedClassObjectString);
    }
}
