package org.mocktail;

import static junit.framework.Assert.assertNotNull;

public class MocktailContext {

    private final String recordingDirectory;
    private static MocktailContext context;

    private MocktailContext(String recordingDirectory) {
        this.recordingDirectory = recordingDirectory;
    }

    public String getRecordingDirectory() {
        return recordingDirectory;
    }

    public static MocktailContext getMocktailContext(String rootDirectory) {
        if (null == context) {
            context = new MocktailContext(rootDirectory);
        }
        return context;
    }

    public static MocktailContext getMocktailContext() {
        assertNotNull(context);
        return context;
    }
}
