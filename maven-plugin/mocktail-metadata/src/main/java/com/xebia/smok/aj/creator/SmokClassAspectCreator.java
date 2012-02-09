package com.xebia.smok.aj.creator;

import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.SmokMode;

/**
 * I'll create an aspect for the class defined in Smok
 * 
 */
public class SmokClassAspectCreator extends AbstractSmokAspectCreator {

	public SmokClassAspectCreator(SmokMode smokMode) {
		super(AspectType.CLASS_ASPECT_TYPE, smokMode);
	}

}
