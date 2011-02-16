package com.xebia.smok.aj.creator;

import org.junit.Test;

import com.xebia.smok.SmokObjectMother;

public class AspectJCreatorTest {

	@Test
	public void shouldCreateAspectFromSmoks() throws Exception {
		AspectJCreator.DEFAULT_ASPECTJ_CREATOR.createAspects("directory",
				SmokObjectMother.getSmoksForAspects());
	}
}
