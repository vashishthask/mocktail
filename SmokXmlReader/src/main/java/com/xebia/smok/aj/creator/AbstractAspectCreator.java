package com.xebia.smok.aj.creator;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public abstract class AbstractAspectCreator<C> implements AspectCreator<C> {

	@Override
	public void createAspect(C classObj, File directory) throws Exception {
		String templatedClassObjectString = TemplateProcesser.TEMPLATE_PROCESSER.processTemplate(
				getTemplateDynamicValues(classObj), getTemplateInputStream());
		//TODO Store this string with the class Obj file name
	}

	protected abstract InputStream getTemplateInputStream();

	protected abstract Map<String, Object> getTemplateDynamicValues(C classObj);

}
