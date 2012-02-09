package com.xebia.smok.aj.creator;

import java.io.File;

import org.junit.Test;

import com.xebia.smok.SmokObjectMother;
import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;


public class AbstractAspectCreatorTest {

	
	@Test
	public void testAspectFileCreation() throws Exception {
		AbstractAspectCreator<Smok> abstractAspectCreator = new AbstractSmokAspectCreator(AspectType.CLASS_ASPECT_TYPE, SmokMode.RECORDING_MODE) {
		};
		
		abstractAspectCreator.createAspect(SmokObjectMother.createClassSmok("AspectedClass", ""), new File("C:/sandy/recording"));
	}
}
