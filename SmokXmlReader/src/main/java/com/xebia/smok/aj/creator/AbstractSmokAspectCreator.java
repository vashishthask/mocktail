package com.xebia.smok.aj.creator;

import java.util.HashMap;
import java.util.Map;

import com.xebia.smok.SmokContext;
import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

public abstract class AbstractSmokAspectCreator extends
		AbstractAspectCreator<Smok> {

	public AbstractSmokAspectCreator(AspectType aspectType, SmokMode smokMode) {
		super(aspectType, smokMode);
	}

	protected String getAspectDirectory(Smok smok) {
		return smok.getClassPackageName().replaceAll("\\.", "/");
	}

	protected Map<String, Object> getTemplateParameterValues(Smok smok) {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("className", smok.getClassName());
		contextMap.put("classPackage", smok.getClassPackageName());
		return contextMap;
	}

	protected String getAspectFileName(Smok smok) {
		return smok.getClassName();
	}

}
