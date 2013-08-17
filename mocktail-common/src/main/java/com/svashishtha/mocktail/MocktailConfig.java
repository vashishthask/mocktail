package com.svashishtha.mocktail;

import java.util.Properties;

import org.slf4j.LoggerFactory;

public enum MocktailConfig {
	INSTANCE();
	Properties config = new Properties();
	private final org.slf4j.Logger log = LoggerFactory
            .getLogger(MocktailConfig.class);
	
	
	private MocktailConfig(){
		try {
			config.load(this.getClass().getClassLoader().getResourceAsStream("mocktailconfig.properties"));
		} catch (Exception e) {
			log.error("MocktailConfig could not be loaded from classpath:"+e.getClass().getName()+":message:"+e.getMessage());
		}
	}
	
	public String getProperty(String key){
		return config.getProperty(key);
	}
}
