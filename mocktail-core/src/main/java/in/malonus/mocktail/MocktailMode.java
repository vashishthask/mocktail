package in.malonus.mocktail;

public enum MocktailMode {

    PLAYBACK("playback", "Playback"), RECORDING("recording", "Recorder"), RECORDING_NEW("recording_new", "Recorder");

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
