package com.xebia.smok.aspect.recorder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;

import com.xebia.smok.aj.creator.TemplateProcesser;

public class TestAspectTemplate {

	@Test
	public void testRecordingTemplate() throws Exception {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("name", "sandy");
		String templatedString = TemplateProcesser.TEMPLATE_PROCESSER
				.processTemplate(
						contextMap,
						new ClasspathResourceLoader()
								.getResourceStream("com/xebia/smok/aj/creator/HelloWorld.vm"));

		assertThat(templatedString, is("Hello sandy"));
	}
}
