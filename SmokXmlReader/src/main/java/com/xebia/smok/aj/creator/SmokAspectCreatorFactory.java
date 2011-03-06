package com.xebia.smok.aj.creator;

import com.xebia.smok.xml.domain.Smok;

public class SmokAspectCreatorFactory {

	public static AspectCreator<Smok> getAspectCreator(boolean aspectForCompleteClass) {
		if(aspectForCompleteClass) {
			return new SmokClassAspectCreator();
		} else {
			return new SmokMethodsAspectCreator();
		}
	}
}
