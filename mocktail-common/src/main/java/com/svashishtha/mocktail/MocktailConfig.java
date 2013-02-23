package com.svashishtha.mocktail;

import java.util.Properties;

public enum MocktailConfig {
	INSTANCE();
	Properties config = new Properties();
	
	
	private MocktailConfig(){
		try {
			config.load(this.getClass().getClassLoader().getResourceAsStream("mocktailconfig.properties"));
		} catch (Exception e) {
			System.err.println("MocktailConfig could not be loaded from classpath:"+e.getClass().getName()+":message:"+e.getMessage());
		}
	}
	
	public String getProperty(String key){
		return config.getProperty(key);
	}
}
