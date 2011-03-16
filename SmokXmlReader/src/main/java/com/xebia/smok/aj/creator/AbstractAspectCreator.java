package com.xebia.smok.aj.creator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.xebia.smok.xml.domain.AspectType;
import com.xebia.smok.xml.domain.SmokMode;

public abstract class AbstractAspectCreator<C> implements AspectCreator<C> {

	private final AspectType aspectType;
	private final SmokMode smokMode;

	public AbstractAspectCreator(AspectType aspectType, SmokMode smokMode ){
		this.aspectType = aspectType;
		this.smokMode = smokMode;
	}
	
	
	@Override
	public void createAspect(C classObj, File directory) throws Exception {
		String templatedClassObjectString = TemplateProcesser.TEMPLATE_PROCESSER.processTemplate(
				getTemplateParameterValues(classObj), getAspectTemplateInputStream());
		createAspectFile(getAspectFileName(classObj), directory, templatedClassObjectString);
	}

	protected void createAspectFile(String fileName, File directory,
			String templatedObjectString) throws IOException {
		if(!directory.exists()){
			try {
				directory.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File file = new File(directory, "Aspect"+fileName+".aj");
		FileWriter aspectOs = new FileWriter(file);
		aspectOs.write(templatedObjectString);
		aspectOs.close();
	}

	protected InputStream getAspectTemplateInputStream() {
		StringBuffer aspectTemplatePath = new StringBuffer("com/xebia/smok/aj/creator/");
		aspectTemplatePath.append(aspectType.getAspectTypeDirectory()).append("/");
		aspectTemplatePath.append(smokMode.getModeDirectory()).append("/");
		aspectTemplatePath.append("template.vm");
		return new ClasspathResourceLoader()
		.getResourceStream(aspectTemplatePath.toString());
	}

	protected abstract Map<String, Object> getTemplateParameterValues(C classObj);

	protected abstract String getAspectFileName(C classObj);

}
