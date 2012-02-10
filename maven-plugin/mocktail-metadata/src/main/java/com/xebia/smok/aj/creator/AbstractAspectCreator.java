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

	public AbstractAspectCreator(AspectType aspectType, SmokMode smokMode) {
		this.aspectType = aspectType;
		this.smokMode = smokMode;
	}

	@Override
	/**
	 * I'll create the contents of the aspect file for that
	 * We have to get the dynamic values <code>getTemplateParameterValues</code>
	 * Input stream of template recording/playback class/method
	 */
	public void createAspect(C classObj, File aspectsRootDirectory)
			throws Exception {
		
		String templatedClassObjectString = TemplateProcesser.TEMPLATE_PROCESSER
				.processTemplate(getTemplateParameterValues(classObj),
						getAspectTemplateInputStream());
		createAspectFile(classObj, getAspectFileName(classObj),
				aspectsRootDirectory, templatedClassObjectString);
	}

	/**
	 * I'll create aspect in the the aspect directory
	 */
	protected void createAspectFile(C classObj, String aspectFileName,
			File aspectsRootDirecotry, String templatedObjectString)
			throws IOException {
		File aspectFileDirectory = new File(aspectsRootDirecotry,
				getAspectDirectory(classObj));
		if (!aspectFileDirectory.exists()) {
			aspectFileDirectory.mkdirs();
		}
		File file = new File(aspectFileDirectory, "Aspect" + aspectFileName + ".aj");
		FileWriter aspectOs = new FileWriter(file);
		aspectOs.write(templatedObjectString);
		aspectOs.close();
	}

	protected InputStream getAspectTemplateInputStream() {
		StringBuffer aspectTemplatePath = new StringBuffer(
				"com/xebia/smok/aj/creator/");
		aspectTemplatePath.append(aspectType.getAspectTypeDirectory()).append(
				"/");
		aspectTemplatePath.append(smokMode.getModeDirectory()).append("/");
		aspectTemplatePath.append("template.vm");
		return new ClasspathResourceLoader()
				.getResourceStream(aspectTemplatePath.toString());
	}

	protected abstract String getAspectDirectory(C classObj);

	protected abstract String getAspectFileName(C classObj);

	protected abstract Map<String, Object> getTemplateParameterValues(C classObj);


}
