package in.malonus.mocktail;

import org.junit.Assert;
import org.junit.Test;

import in.malonus.mocktail.MocktailConfig;

public class MocktailConfigTest {

	@Test
	public void testGetProperty() {
		String recordingDir = MocktailConfig.INSTANCE.getProperty("recordingDir");
		Assert.assertEquals("src/test/resources", recordingDir);
	}
}