package com.xebia.smok.aj.creator;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public enum TemplateProcesser {
	TEMPLATE_PROCESSER;
	private VelocityEngine ve;


	private TemplateProcesser() {
		ve = new VelocityEngine();
	}

	public String processTemplate(Map<String, Object> dynamicValues,
			InputStream template) throws Exception {
		VelocityContext context = new VelocityContext();
		for (Map.Entry<String, Object> contextEntry : dynamicValues.entrySet()) {
			context.put(contextEntry.getKey(), contextEntry.getValue());
		}
		StringWriter writer = new StringWriter();
		ve.evaluate(context, writer, "", template);
		return writer.toString();
	}

}
