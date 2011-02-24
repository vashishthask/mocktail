package com.xebia.smok.aj.creator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.xebia.smok.xml.domain.Smok;

/**
 * I'll create an aspect for the class defined in Smok
 *
 */
public class SmokClassAspectCreator extends AbstractAspectCreator<Smok> {

	protected InputStream getAspectTemplateInputStream() {
		return new ClasspathResourceLoader()
				.getResourceStream("com/xebia/smok/aj/creator/ClassRecordingAspect.vm");
	}

	protected Map<String, Object> getTemplateParameterValues(Smok smok) {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("className", smok.getClassName());
		return contextMap;
	}
	
	@Override
	protected String getAspectFileName(Smok classObj) {
		return classObj.getClassName();
	}

}
