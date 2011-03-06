package com.xebia.smok.aj.creator;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.xebia.smok.SmokObjectMother;

public class SmoksAspectsCreatorTest {

	@Test
	public void shouldCreateSmoksAspects() throws Exception {
		int aspectsCreated = SmoksAspectsCreator.ASPECTS_CREATOR.createAspects(SmokObjectMother.getSmoksForAspects());
		assertEquals(aspectsCreated, 2);
	}

}
