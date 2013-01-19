package org.apache.ws.commons.tcpmon;

import java.io.IOException;
import java.util.Properties;

public enum MocktailConfig {
	INSTANCE();
	Properties config = new Properties();
	
	
	private MocktailConfig(){
		try {
			config.load(this.getClass().getClassLoader().getResourceAsStream("mocktailconfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key){
		return config.getProperty(key);
	}
}
