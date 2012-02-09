package com.xebia.smok.aj.creator;

import com.xebia.smok.xml.domain.Smok;
import com.xebia.smok.xml.domain.SmokMode;

public class SmokAspectCreatorFactory {

	public static AspectCreator<Smok> getAspectCreator(boolean aspectForCompleteClass, SmokMode smokMode) {
		if(aspectForCompleteClass) {
			return new SmokClassAspectCreator(smokMode);
		} else {
			return new SmokMethodsAspectCreator(smokMode);
		}
	}
}
