package com.xebia.smok.aj.creator;

import org.junit.Test;

import com.xebia.smok.SmokObjectMother;


public class SmokMethodsAspectCreatorTest {
	
	@Test
	public void shouldCreateMethodAspects() throws Exception {
		new SmokMethodsAspectCreator().createAspect(SmokObjectMother.createMethodSmok("FQCN","method1", "method2"));
	}

}
