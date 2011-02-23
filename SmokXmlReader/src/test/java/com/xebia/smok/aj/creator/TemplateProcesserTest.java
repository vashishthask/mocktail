package com.xebia.smok.aj.creator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;

public class TemplateProcesserTest {

	@Test
	public void shouldCreateAspect() throws Exception {
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
