package com.svashishtha.mocktail.metadata.xml.domain;

public enum AspectType {
    CLASS_ASPECT_TYPE("class"), METHODS_ASPECT_TYPE("methods");

    private String aspectType;

    private AspectType(String aspectType) {
        this.aspectType = aspectType;
    }

    public String getAspectTypeDirectory() {
        return aspectType;
    }

}
