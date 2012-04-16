package org.mocktail.aj.creator;

import org.mocktail.xml.domain.AspectType;
import org.mocktail.xml.domain.MocktailMode;

/**
 * I'll create an aspect for the class defined in Mocktail
 * 
 */
public class MocktailClassAspectCreator extends AbstractMocktailAspectCreator {

    public MocktailClassAspectCreator(MocktailMode mocktailMode) {
        super(AspectType.CLASS_ASPECT_TYPE, mocktailMode);
    }

}
