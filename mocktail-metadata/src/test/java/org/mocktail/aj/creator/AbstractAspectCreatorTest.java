package org.mocktail.aj.creator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mocktail.MocktailContainer;
import org.mocktail.MocktailObjectMother;
import org.mocktail.xml.domain.AspectType;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;

public class AbstractAspectCreatorTest {

    @Rule
    public TemporaryFolder temporaryAspectDir = new TemporaryFolder();

    @Test
    public void testAspectFileCreation() throws Exception {
        MocktailContainer.initializeContainer("");
        AbstractAspectCreator<Mocktail> abstractAspectCreator = new AbstractMocktailAspectCreator(
                AspectType.CLASS_ASPECT_TYPE, MocktailMode.RECORDING_MODE) {
        };
        File aspectsRootDirectory = temporaryAspectDir.newFolder("aspect");
        assertFalse("Aspect already exists", (new File(aspectsRootDirectory,
                "RecorderAspectAspectedClass.aj")).exists());

        abstractAspectCreator.createAspect(
                MocktailObjectMother.createClassMocktail("AspectedClass", ""),
                aspectsRootDirectory);

        assertTrue("Aspect doesn't exists", (new File(aspectsRootDirectory,
                "RecorderAspectAspectedClass.aj")).exists());
    }
}
