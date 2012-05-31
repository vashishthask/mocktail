package org.mocktail.aj.creator;

import java.io.File;

public interface AspectCreator<C> {
    void createAspect(C classObj, File directory);
}