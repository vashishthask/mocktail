package com.xebia.smok.aj.creator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.xebia.smok.xml.domain.Smok;


public class AspectCreatorFactoryTest {
	

	@Test
	public void shouldCreateAspectCreatorForClass() throws Exception {
		AspectCreator<Smok> aspectCreator = SmokAspectCreatorFactory.getAspectCreator(true);
		assertTrue(aspectCreator instanceof SmokClassAspectCreator);
	}

	@Test
	public void shouldCreateAspectCreatorForMethods() throws Exception {
		AspectCreator<Smok> aspectCreator = SmokAspectCreatorFactory.getAspectCreator(false);
		assertTrue(aspectCreator instanceof SmokMethodsAspectCreator);
		
	}
}
