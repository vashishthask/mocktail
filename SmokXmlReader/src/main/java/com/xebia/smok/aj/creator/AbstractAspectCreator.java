package com.xebia.smok.aj.creator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public abstract class AbstractAspectCreator<C> implements AspectCreator<C> {

	@Override
	public void createAspect(C classObj, File directory) throws Exception {
		String templatedClassObjectString = TemplateProcesser.TEMPLATE_PROCESSER.processTemplate(
				getTemplateParameterValues(classObj), getAspectTemplateInputStream());
		createAspectFile(getAspectFileName(classObj), directory, templatedClassObjectString);
	}

	protected void createAspectFile(String fileName, File directory,
			String templatedClassObjectString) throws IOException {
		File file = new File(directory, fileName);
		FileWriter aspectOs = new FileWriter(file);
		aspectOs.write(templatedClassObjectString);
		aspectOs.close();
	}

	protected abstract InputStream getAspectTemplateInputStream();

	protected abstract Map<String, Object> getTemplateParameterValues(C classObj);

	protected abstract String getAspectFileName(C classObj);

}
