package com.xebia.smok.aj.creator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.xebia.smok.SmokContainer;
import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;


public class AbstractAspectCreatorTest {

	@Rule
    public TemporaryFolder temporaryAspectDir= new TemporaryFolder();
	
	@Test
	public void testAspectFileCreation() throws Exception {
		SmokContainer.initializeContainer("");
		AbstractAspectCreator<Smok> abstractAspectCreator = new AbstractSmokAspectCreator(AspectType.CLASS_ASPECT_TYPE, SmokMode.RECORDING_MODE) {};
		File aspectsRootDirectory = temporaryAspectDir.newFolder("aspect");
		assertFalse("Aspect already exists", (new File(aspectsRootDirectory, "AspectAspectedClass.aj")).exists());
		
		abstractAspectCreator.createAspect(SmokObjectMother.createClassSmok("AspectedClass", ""), aspectsRootDirectory);

		assertTrue("Aspect doesn't exists", (new File(aspectsRootDirectory, "AspectAspectedClass.aj")).exists());
	}
}
