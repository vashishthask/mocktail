package org.mocktail.aspect.recorder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;
import org.mocktail.aj.creator.TemplateProcesser;

public class TestAspectTemplate {

    @Test
    public void testRecordingTemplate() throws Exception {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put("name", "sandy");
        String templatedString = TemplateProcesser.TEMPLATE_PROCESSER
                .processTemplate(
                        contextMap,
                        new ClasspathResourceLoader()
                                .getResourceStream("org/mocktail/aj/creator/HelloWorld.vm"));

        assertThat(templatedString, is("Hello sandy"));
    }
}
