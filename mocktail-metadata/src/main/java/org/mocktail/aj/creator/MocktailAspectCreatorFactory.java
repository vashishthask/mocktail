package org.mocktail.aj.creator;

import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;

public class MocktailAspectCreatorFactory {

    public static AspectCreator<Mocktail> getAspectCreator(
            boolean aspectForCompleteClass, MocktailMode mocktailMode) {
        if (aspectForCompleteClass) {
            return new MocktailClassAspectCreator(mocktailMode);
        } else {
            return new MocktailMethodsAspectCreator(mocktailMode);
        }
    }
}
