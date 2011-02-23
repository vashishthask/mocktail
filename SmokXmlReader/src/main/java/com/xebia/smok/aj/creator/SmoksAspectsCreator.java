package com.xebia.smok.aj.creator;

import java.util.List;

import com.xebia.smok.xml.domain.Smok;

public enum SmoksAspectsCreator {
	ASPECTS_CREATOR;
	
	public int createAspects(List<Smok> smoksForAspects) {
		int aspectsCreated = 0;
		AspectCreator<Smok> smokAspectCreator;
		for (Smok smok : smoksForAspects) {
			smokAspectCreator = SmokAspectCreatorFactory.getAspectCreator(smok.onlyForClass());
			try {
				smokAspectCreator.createAspect(smok);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			aspectsCreated++;
		}
		return aspectsCreated;
	}

}
