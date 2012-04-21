package org.mocktail.aj.creator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.mocktail.MocktailContainer;
import org.mocktail.xml.domain.AspectType;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;

public class MocktailAspectCreator extends
        AbstractAspectCreator<Mocktail> {

    public MocktailAspectCreator(AspectType aspectType,
            MocktailMode mocktailMode) {
        super(aspectType, mocktailMode);
    }

    protected String getAspectDirectory(Mocktail mocktail) {
        String packagePath = "";
        if (null != mocktail.getClassPackageName()) {
            packagePath = mocktail.getClassPackageName().replace(".", File.separator);
        }
        return packagePath;
    }

    protected Map<String, Object> getTemplateParameterValues(Mocktail mocktail) {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put("fqcn", mocktail.getClassFQCN());
        contextMap.put("className", mocktail.getClassName());
        String recordingDirectory = MocktailContainer.getInstance().getRecordingDirectory();
        contextMap.put("recordingDirectory", recordingDirectory);
        // contextMap.put("classPackage", mocktail.getClassPackageName());
        return contextMap;
    }

    protected String getAspectFileName(Mocktail mocktail) {
        return super.getAspectFileName(mocktail) + "Aspect"
                + mocktail.getClassName();
    }

}
