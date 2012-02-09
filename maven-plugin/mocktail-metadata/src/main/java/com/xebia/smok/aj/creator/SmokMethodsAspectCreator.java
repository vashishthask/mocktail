package com.xebia.smok.aj.creator;

import java.util.Map;

import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

/**
 * I'll create an aspect for methods defined in Smok
 * 
 */
public class SmokMethodsAspectCreator extends AbstractSmokAspectCreator {

	public SmokMethodsAspectCreator(SmokMode smokMode) {
		super(AspectType.METHODS_ASPECT_TYPE, smokMode);
	}

	@Override
	protected Map<String, Object> getTemplateParameterValues(Smok smok) {
		Map<String, Object> contextMap = super.getTemplateParameterValues(smok);
		contextMap.put("methods", smok.getMethods());
		return contextMap;
	}
	
}
