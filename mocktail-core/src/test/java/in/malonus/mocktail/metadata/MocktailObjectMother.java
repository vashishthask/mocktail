package in.malonus.mocktail.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.malonus.mocktail.metadata.xml.domain.Mocktail;

public class MocktailObjectMother {

    public static List<Mocktail> getMocktailsForAspects() {
        List<Mocktail> mocktails = new ArrayList<Mocktail>();

        mocktails.add(getMocktail("TemplateProcesser", "in.malonus.mocktail.metadata.aj.creator", "a", "b"));
        mocktails.add(getMocktail("Mocktail", "in.malonus.mocktail.metadata.xml.domain"));
        return mocktails;
    }

    private static Mocktail getMocktail(String className, String packageName, String... methodsName) {
        Mocktail mocktail = new Mocktail();
        mocktail.setClassName(className);
        mocktail.setClassPackageName(packageName);
        mocktail.setMethods(Arrays.asList(methodsName));
        return mocktail;
    }

    public static Mocktail createClassMocktail(String className, String pcakageName) {
        return getMocktail(className, pcakageName);
    }

    public static Mocktail createMethodMocktail(String className, String packageName, String... methodsName) {
        return getMocktail(className, packageName, methodsName);
    }

}
