package com.xebia.smok.aj.creator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.xebia.smok.xml.domain.Smok;
/**
 * I'll create an aspect for methods defined in Smok
 *
 */
public class SmokMethodsAspectCreator extends AbstractAspectCreator<Smok>{

	@Override
	protected InputStream getAspectTemplateInputStream() {
		return new ClasspathResourceLoader()
		.getResourceStream("com/xebia/smok/aj/creator/MethodsRecordingAspect.vm");
	}

	@Override
	protected Map<String, Object> getTemplateDynamicValues(Smok smok) {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("className", smok.getClassName());
		contextMap.put("methods", smok.getMethods());
		return contextMap;
	}

}
