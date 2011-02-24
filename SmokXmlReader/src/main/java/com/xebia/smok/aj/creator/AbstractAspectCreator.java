package com.xebia.smok.aj.creator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public abstract class AbstractAspectCreator<C> implements AspectCreator<C> {

	@Override
	public void createAspect(C classObj, File directory) throws Exception {
		String templatedClassObjectString = TemplateProcesser.TEMPLATE_PROCESSER.processTemplate(
				getTemplateDynamicValues(classObj), getAspectTemplateInputStream());
		createAspectFile(classObj.getClass().getName(), directory, templatedClassObjectString);
	}

	private void createAspectFile(String fileName, File directory,
			String templatedClassObjectString) throws IOException {
		File file = new File(directory, fileName);
		FileWriter aspectOs = new FileWriter(file);
		aspectOs.write(templatedClassObjectString);
		aspectOs.close();
	}

	protected abstract InputStream getAspectTemplateInputStream();

	protected abstract Map<String, Object> getTemplateDynamicValues(C classObj);

}
