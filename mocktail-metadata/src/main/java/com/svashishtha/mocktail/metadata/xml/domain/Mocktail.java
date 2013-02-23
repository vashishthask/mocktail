package com.svashishtha.mocktail.metadata.xml.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Mocktail {

    private String className;
    private String classPackageName = "";
    private List<String> methods;
    private String targetImpl;

    public String getTargetImpl() {
        return targetImpl;
    }

    public void setTargetImpl(String targetImpl) {
        this.targetImpl = targetImpl;
    }

    public Mocktail() {
        methods = new ArrayList<String>();
    }

    public String getClassName() {
        return className;
    }
    
    

    public List<String> getMethods() {
        return methods;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassPackageName(String classPackageName) {
        this.classPackageName = classPackageName;
    }

    public String getClassFQCN() {
        String fqcn = "";
        if (null != classPackageName && this.classPackageName.length() > 0) {
            fqcn = fqcn + this.classPackageName + ".";
        }
        return fqcn + this.className;
    }

    public String getClassPackageName() {
        return this.classPackageName;
    }

    public void setMethods(List<String> methods) {
        this.methods.addAll(methods);
    }

    public boolean onlyForClass() {
        return methods == null || methods.size() == 0;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
