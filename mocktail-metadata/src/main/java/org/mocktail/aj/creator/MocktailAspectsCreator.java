package org.mocktail.aj.creator;

import java.io.File;
import java.util.List;

import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;

public enum MocktailAspectsCreator {
    ASPECTS_CREATOR;

    public int createAspects(List<Mocktail> mocktailsForAspects,
            File aspectsDirectory, MocktailMode mocktailMode) {
        int aspectsCreated = 0;
        AspectCreator<Mocktail> mocktailAspectCreator;
        for (Mocktail mocktail : mocktailsForAspects) {
            mocktailAspectCreator = MocktailAspectCreatorFactory
                    .getAspectCreator(mocktail.onlyForClass(), mocktailMode);
            try {
                mocktailAspectCreator.createAspect(mocktail, aspectsDirectory);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            aspectsCreated++;
        }
        return aspectsCreated;
    }

}
