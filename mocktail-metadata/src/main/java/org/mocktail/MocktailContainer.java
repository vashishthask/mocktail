package org.mocktail;

import org.mocktail.repository.DiskObjectRepository;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.util.HashCodeIdGenerator;
import org.mocktail.util.UniqueIdGenerator;
import org.mocktail.xml.reader.MocktailXmlReader;
import org.mocktail.xml.reader.XStreamMocktailXmlReader;

public class MocktailContainer {

    private static XStreamMocktailXmlReader mocktailXmlReader = new XStreamMocktailXmlReader();
    private static ObjectRepository objectRepository = new DiskObjectRepository();
    private static MocktailContext mocktailContext;
    private static UniqueIdGenerator uniqueIdGenerator = new HashCodeIdGenerator();

    public static void initializeContainer(String recordingDirecotry) {
        mocktailContext = MocktailContext
                .getMocktailContext(recordingDirecotry);
    }

    public static MocktailXmlReader getMocktailXmlReader() {
        return mocktailXmlReader;
    }

    public static ObjectRepository getObjectRepository() {
        return objectRepository;
    }

    public static MocktailContext getMocktailContext() {
        return mocktailContext;
    }

    public static UniqueIdGenerator getUniqueIdGenerator() {
        return uniqueIdGenerator;
    }

}
