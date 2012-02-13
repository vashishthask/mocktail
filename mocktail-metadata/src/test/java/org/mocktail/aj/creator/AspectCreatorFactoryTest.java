package org.mocktail.aj.creator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;


public class AspectCreatorFactoryTest {
	

	@Test
	public void shouldCreateRecordingAspectCreatorForClass() throws Exception {
		AspectCreator<Mocktail> aspectCreator = MocktailAspectCreatorFactory.getAspectCreator(true, MocktailMode.RECORDING_MODE);
		assertTrue(aspectCreator instanceof MocktailClassAspectCreator);
	}

	@Test
	public void shouldCreateRecordingAspectCreatorForMethods() throws Exception {
		AspectCreator<Mocktail> aspectCreator = MocktailAspectCreatorFactory.getAspectCreator(false, MocktailMode.RECORDING_MODE);
		assertTrue(aspectCreator instanceof MocktailMethodsAspectCreator);
		
	}
}
