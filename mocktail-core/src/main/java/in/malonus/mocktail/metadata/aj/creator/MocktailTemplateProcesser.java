package in.malonus.mocktail.metadata.aj.creator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.malonus.mocktail.MocktailMode;
import in.malonus.mocktail.metadata.MocktailContainer;
import in.malonus.mocktail.metadata.xml.domain.AspectType;
import in.malonus.mocktail.metadata.xml.domain.Mocktail;

public class MocktailTemplateProcesser {

    private static final Logger LOGGER = LoggerFactory.getLogger(MocktailTemplateProcesser.class);

    String processTemplate(Mocktail mocktail, AspectType aspectType, MocktailMode mocktailMode) {
        VelocityContext context = new VelocityContext();
        Map<String, Object> dynamicValues = getTemplateParameterValues(mocktail, aspectType, mocktailMode);

        for (Map.Entry<String, Object> contextEntry : dynamicValues.entrySet()) {
            context.put(contextEntry.getKey(), contextEntry.getValue());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAspectTemplate(aspectType, mocktailMode)));

        StringWriter writer = new StringWriter();
        try {
            VelocityEngine ve = new VelocityEngine();

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

    Map<String, Object> getTemplateParameterValues(Mocktail mocktail, AspectType aspectType,
            MocktailMode mocktailMode) {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put("fqcn", mocktail.getClassFQCN());
        contextMap.put("className", mocktail.getClassName());
        contextMap.put("packageName", mocktail.getClassPackageName());
        contextMap.put("mocktailMode", mocktailMode.getModeDirectory());
        String targetImpl = mocktail.getTargetImpl();
        if (StringUtils.isNotEmpty(targetImpl)) {
            contextMap.put("targetImpl", targetImpl);
        }
        String recordingDirectory = MocktailContainer.getInstance().getRecordingDirectory();
        contextMap.put("recordingDirectory", recordingDirectory);
        if (AspectType.METHODS_ASPECT_TYPE.equals(aspectType)) {
            contextMap.put("methods", mocktail.getMethods());
        }
        return contextMap;
    }

    private InputStream getAspectTemplate(AspectType aspectType, MocktailMode mocktailMode) {
        StringBuffer aspectTemplatePath = new StringBuffer("in/malonus/mocktail/aj/creator/");
        aspectTemplatePath.append(aspectType.getAspectTypeDirectory()).append("/");
        aspectTemplatePath.append(mocktailMode.getModeDirectory()).append("/");
        aspectTemplatePath.append("template.vm");
        return new ClasspathResourceLoader().getResourceStream(aspectTemplatePath.toString());
    }

    /**
     * I'll create aspect in the the aspect directory
     */
    private void createAspectFile(Mocktail classObj, String aspectFileName, File aspectsRootDirecotry,
            String templatedObjectString) {
        File aspectFileDirectory = new File(aspectsRootDirecotry, getAspectDirectory(classObj));
        if (!aspectFileDirectory.exists()) {
            aspectFileDirectory.mkdirs();
        }
        File file = new File(aspectFileDirectory, aspectFileName + ".java");
        LOGGER.debug("file path:" + file.getAbsolutePath());
        try {
            FileWriter aspectOs = new FileWriter(file);
            aspectOs.write(templatedObjectString);
            aspectOs.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getAspectDirectory(Mocktail mocktail) {
        String packagePath = "";
        if (null != mocktail.getClassPackageName()) {
            packagePath = mocktail.getClassPackageName().replace(".", File.separator);
        }
        return packagePath;
    }

    public void createAspect(Mocktail mocktail, AspectType aspectType, MocktailMode mocktailMode,
            File aspectsRootDirectory) {
        String templateString = processTemplate(mocktail, aspectType, mocktailMode);
        LOGGER.debug(templateString);
        createAspectFile(mocktail, getAspectFileName(mocktail, mocktailMode), aspectsRootDirectory, templateString);
    }

    private String getAspectFileName(Mocktail mocktail, MocktailMode mocktailMode) {
        return mocktailMode.getFilePrefixForMode() + "Aspect" + mocktail.getClassName();
    }
}