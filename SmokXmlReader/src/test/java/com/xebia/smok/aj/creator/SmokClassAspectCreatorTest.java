package com.xebia.smok.aj.creator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.xebia.smok.SmokObjectMother;


public class SmokClassAspectCreatorTest {

	@Test
	public void shouldCreateAspectForClass() throws Exception {
		File aspectDir = new File("C:\\aspectsDir");
		int filesInAspectsDir = aspectDir.list().length;
		new SmokClassAspectCreator().createAspect(SmokObjectMother.createClassSmok("FQCN"),aspectDir);
		assertThat(filesInAspectsDir + 1, is(aspectDir.list().length));
	}
}
