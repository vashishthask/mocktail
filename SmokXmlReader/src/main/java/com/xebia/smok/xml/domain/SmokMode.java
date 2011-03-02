package com.xebia.smok.xml.domain;

/**
 * I'll define the mode in which the smok runs
 * Two modes that I've right now are playback and recording
 * @author sandeep
 *
 */
public enum SmokMode {
	
	PLAYBACK_MODE("playback"), RECORDING_MODE("recording");
	
	private final String mode;

	private SmokMode(String mode) {
		this.mode = mode;
	}
	
	public String getModeDirectory(){
		return mode;
	}

}
