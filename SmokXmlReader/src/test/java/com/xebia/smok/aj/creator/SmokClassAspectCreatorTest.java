package com.xebia.smok.aj.creator;

import org.junit.Test;

import com.xebia.smok.SmokObjectMother;


public class SmokClassAspectCreatorTest {

	@Test
	public void shouldCreateAspectForClass() throws Exception {
		new SmokClassAspectCreator().createAspect(SmokObjectMother.createClassSmok("FQCN"));
	}
}
