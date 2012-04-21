package org.mocktail.aj.creator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public enum TemplateProcesser {
    TEMPLATE_PROCESSER;
    private VelocityEngine ve;

    private TemplateProcesser() {
        ve = new VelocityEngine();
    }

    public String processTemplate(Map<String, Object> dynamicValues,
            InputStream template) {
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> contextEntry : dynamicValues.entrySet()) {
            context.put(contextEntry.getKey(), contextEntry.getValue());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(template));
        StringWriter writer = new StringWriter();
        try {
            ve.evaluate(context, writer, "", reader);
        } catch (ParseErrorException e) {
            throw new IllegalStateException(e);
        } catch (MethodInvocationException e) {
            throw new IllegalStateException(e);
        } catch (ResourceNotFoundException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }

}
