package com.xebia.smok.aj.creator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;


public class AspectCreatorFactoryTest {
	

	@Test
	public void shouldCreateRecordingAspectCreatorForClass() throws Exception {
		AspectCreator<Smok> aspectCreator = SmokAspectCreatorFactory.getAspectCreator(true, SmokMode.RECORDING_MODE);
		assertTrue(aspectCreator instanceof SmokClassAspectCreator);
	}

	@Test
	public void shouldCreateRecordingAspectCreatorForMethods() throws Exception {
		AspectCreator<Smok> aspectCreator = SmokAspectCreatorFactory.getAspectCreator(false, SmokMode.RECORDING_MODE);
		assertTrue(aspectCreator instanceof SmokMethodsAspectCreator);
		
	}
}
