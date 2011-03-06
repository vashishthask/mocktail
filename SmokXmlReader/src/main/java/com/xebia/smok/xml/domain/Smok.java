package com.xebia.smok.xml.domain;

import java.util.ArrayList;
import java.util.List;

public class Smok {

	private String className;
	private List<String> methods;

	public Smok() {
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

	public void setMethods(List<String> methods) {
		this.methods.addAll(methods);
	}

	public boolean onlyForClass() {
		return methods.size() == 0;
	}

}
