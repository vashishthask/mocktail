package org.mocktail.aj.creator;

import java.util.Map;

import org.mocktail.xml.domain.AspectType;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;

/**
 * I'll create an aspect for methods defined in Mocktail
 * 
 */
public class MocktailMethodsAspectCreator extends AbstractMocktailAspectCreator {

	public MocktailMethodsAspectCreator(MocktailMode mocktailMode) {
		super(AspectType.METHODS_ASPECT_TYPE, mocktailMode);
	}

	@Override
	protected Map<String, Object> getTemplateParameterValues(Mocktail mocktail) {
		Map<String, Object> contextMap = super.getTemplateParameterValues(mocktail);
		contextMap.put("methods", mocktail.getMethods());
		return contextMap;
	}
	
}
