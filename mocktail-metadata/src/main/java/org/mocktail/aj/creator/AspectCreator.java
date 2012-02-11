package org.mocktail.aj.creator;

import java.io.File;

/**
 * I'll create aspects for class of type <C>
 * 
 * @param <C>
 */
public interface AspectCreator<C> {

	void createAspect(C classObj, File directory) throws Exception;

}
