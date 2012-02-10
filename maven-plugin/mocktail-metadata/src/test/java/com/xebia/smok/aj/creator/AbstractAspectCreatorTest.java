package com.xebia.smok.aj.creator;

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
		abstractAspectCreator.createAspect(SmokObjectMother.createClassSmok("AspectedClass", ""), aspectsRootDirectory);
	}
}
