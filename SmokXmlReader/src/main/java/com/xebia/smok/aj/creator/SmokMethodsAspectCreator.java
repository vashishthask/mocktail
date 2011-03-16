package com.xebia.smok.aj.creator;

import java.util.HashMap;
import java.util.Map;

import com.xebia.smok.SmokContext;
import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

/**
 * I'll create an aspect for methods defined in Smok
 * 
 */
public class SmokMethodsAspectCreator extends AbstractAspectCreator<Smok> {

	public SmokMethodsAspectCreator(SmokMode smokMode) {
		super(AspectType.METHODS_ASPECT_TYPE, smokMode);
	}

	@Override
	protected Map<String, Object> getTemplateParameterValues(Smok smok) {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("className", smok.getClassName());
		contextMap.put("methods", smok.getMethods());
		contextMap.put("recordingDirectoryPath", SmokContext.getSmokContext().getRootDirectory().replaceAll("\\\\","/"));
		return contextMap;
	}

	@Override
	protected String getAspectFileName(Smok classObj) {
		return classObj.getClassName();
	}

}
