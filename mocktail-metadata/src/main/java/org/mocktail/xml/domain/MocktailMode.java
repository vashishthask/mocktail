package org.mocktail.xml.domain;

/**
 * I'll define the mode in which the mocktail runs
 * Two modes that I've right now are playback and recording
 * @author sandeep
 *
 */
public enum MocktailMode {
	
	PLAYBACK_MODE("playback"), RECORDING_MODE("recording");
	
	private final String mode;

	private MocktailMode(String mode) {
		this.mode = mode;
	}
	
	public String getModeDirectory(){
		return mode;
	}

}
