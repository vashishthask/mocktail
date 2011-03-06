package com.xebia.smok.aj.creator;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.xebia.smok.xml.domain.Smok;

public enum AspectJCreator {
	DEFAULT_ASPECTJ_CREATOR;
	private VelocityEngine ve;
	private InputStream classAspectTemplate;
	private InputStream methodsAspectTemplate;

	private AspectJCreator() {
		ve = new VelocityEngine();
		try {
			ve.init();
			classAspectTemplate = new ClasspathResourceLoader()
					.getResourceStream("com/xebia/smok/aj/creator/ClassRecordingAspect.vm");
			methodsAspectTemplate = new ClasspathResourceLoader()
			.getResourceStream("com/xebia/smok/aj/creator/MethodsRecordingAspect.vm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAspects(String directory, List<Smok> smoks)
			throws Exception {
		for (Smok smok : smoks) {
			try {
				createAspectForSmok(directory, smok);
			} catch (Exception e) {
				System.out.println("Unable to create aspect file for smok "
						+ smok);
				e.printStackTrace();
			}
		}

	}

	protected void createAspectForSmok(String directory, Smok smok)
			throws Exception {

		VelocityContext context = new VelocityContext();
		context.put("className", smok.getClassName());
		if(smok.getMethods().isEmpty()) {
			StringWriter writer = new StringWriter();
			ve.evaluate(context, writer, "", classAspectTemplate);
			System.out.println("Aspect File: " + writer.toString());
		} else {
			context.put("methods", smok.getMethods());
			StringWriter writer = new StringWriter();
			ve.evaluate(context, writer, "", methodsAspectTemplate);
			System.out.println("Aspect File: " + writer.toString());
		}

	}

}
