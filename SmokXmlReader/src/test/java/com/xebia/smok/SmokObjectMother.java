package com.xebia.smok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xebia.smok.aj.creator.TemplateProcesser;
import com.xebia.smok.xml.domain.Smok;

public class SmokObjectMother {

	public static List<Smok> getSmoksForAspects() {
		List<Smok> smoks = new ArrayList<Smok>();
		
		smoks.add(getSmok(TemplateProcesser.class.getName(), "a", "b"));
		smoks.add(getSmok(Smok.class.getName(),"PACKAGE"));
		return smoks;
	}

	private static Smok getSmok(String className, String packageName, String...methodsName) {
		Smok smok = new Smok();
		smok.setClassName(className);
		smok.setClassPackageName(packageName);
		smok.setMethods(Arrays.asList(methodsName));
		return smok;
	}

	public static Smok createClassSmok(String className) {
		return getSmok(className, "");
	}

	public static Smok createMethodSmok(String className, String packageName, String... methodsName) {
		return getSmok(className, packageName, methodsName);
	}

}
