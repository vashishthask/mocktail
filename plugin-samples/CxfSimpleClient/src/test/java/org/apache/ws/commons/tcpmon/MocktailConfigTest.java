package org.apache.ws.commons.tcpmon;

import junit.framework.Assert;

import org.junit.Test;

public class MocktailConfigTest {

	@Test
	public void testGetProperty() {
		String recordingDir = MocktailConfig.INSTANCE.getProperty("recordingDir");
		Assert.assertEquals("src/test/resources", recordingDir);
	}

}
