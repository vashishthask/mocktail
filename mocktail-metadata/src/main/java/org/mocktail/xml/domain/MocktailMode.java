package org.mocktail.xml.domain;

/**
 * I'll define the mode in which the mocktail runs Two modes that I've right now
 * are playback and recording
 * 
 * @author sandeep
 * 
 */
public enum MocktailMode {

    PLAYBACK("playback", "Playback"), RECORDING("recording",
            "Recorder"), RECORDING_NEW("recording_new",
                    "Recorder");
    

    private final String mode;
    private final String filePrefix;

    private MocktailMode(String mode, String filePrefix) {
        this.mode = mode;
        this.filePrefix = filePrefix;
    }

    public String getModeDirectory() {
        return mode;
    }

    public String getFilePrefixForMode() {
        return this.filePrefix;
    }

}
