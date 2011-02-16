package com.xebia.smok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xebia.smok.aj.creator.AspectJCreator;
import com.xebia.smok.xml.domain.Smok;

public class SmokObjectMother {

	public static List<Smok> getSmoksForAspects() {
		List<Smok> smoks = new ArrayList<Smok>();
		
		smoks.add(getSmok(AspectJCreator.class.getName(), "a", "b"));
		smoks.add(getSmok(Smok.class.getName()));
		return smoks;
	}

	private static Smok getSmok(String className, String...methodsName) {
		Smok smok = new Smok();
		smok.setClassName(className);
		smok.setMethods(Arrays.asList(methodsName));
		return smok;
	}

}
