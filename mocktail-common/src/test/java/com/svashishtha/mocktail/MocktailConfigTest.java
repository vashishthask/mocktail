package com.svashishtha.mocktail;

import junit.framework.Assert;

import org.junit.Test;

import com.svashishtha.mocktail.MocktailConfig;

public class MocktailConfigTest {

	@Test
	public void testGetProperty() {
		String recordingDir = MocktailConfig.INSTANCE.getProperty("recordingDir");
		Assert.assertEquals("src/test/resources", recordingDir);
	}

}
