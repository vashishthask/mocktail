package in.malonus.tcpcache;

public enum CacheMode {
    RECORDING, PLAYBACK, RECORDING_NEW;
    
    public String toString() {
        return this.name().toLowerCase();
    }
}
