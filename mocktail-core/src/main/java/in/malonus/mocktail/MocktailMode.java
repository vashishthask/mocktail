package in.malonus.mocktail;

public enum MocktailMode {

    PLAYBACK("Playback"), RECORDING("Recorder"), RECORDING_NEW("Recorder");

    private final String filePrefix;

    private MocktailMode(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String getFilePrefix() {
        return this.filePrefix;
    }
    
    public String toString() {
        return this.name().toLowerCase();
    }
}
