package com.xebia.smok.aj.creator;

import java.util.HashMap;
import java.util.Map;

import com.xebia.smok.SmokContext;
import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

/**
 * I'll create an aspect for the class defined in Smok
 * 
 */
public class SmokClassAspectCreator extends AbstractSmokAspectCreator {

	public SmokClassAspectCreator(SmokMode smokMode) {
		super(AspectType.CLASS_ASPECT_TYPE, smokMode);
	}

	protected Map<String, Object> getTemplateParameterValues(Smok smok) {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("className", smok.getClassName());
		String recordingDirectoryPath = SmokContext.getSmokContext()
				.getRootDirectory()
				+ "/"
				+ getAspectDirectory(smok);
		contextMap.put("recordingDirectoryPath", recordingDirectoryPath);
		return contextMap;
	}

}
