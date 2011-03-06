package com.xebia;

import org.codehaus.mojo.aspectj.AjcCompileMojo;
import org.junit.Test;


public class SmokMojoTest {
	@Test
	public void shouldSetValues(){
		SmokMojo smokMojo = new SmokMojo();
		AjcCompileMojo ajcCompileMojo = new AjcCompileMojo();
		smokMojo.setValue(ajcCompileMojo.getClass().getSuperclass(),ajcCompileMojo,"source","1.5");
		smokMojo.setValue(ajcCompileMojo.getClass().getSuperclass(),ajcCompileMojo,"target","1.5");
		
	}

}
