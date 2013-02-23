package com.svashishtha.mocktail.metadata.aj.creator;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import com.svashishtha.mocktail.MocktailMode;
import com.svashishtha.mocktail.metadata.MocktailContainer;
import com.svashishtha.mocktail.metadata.MocktailObjectMother;

@Ignore
public class MocktailAspectsCreatorTest {

//    @Test
//    public void testCreateAspects() throws Exception {
//        MocktailContainer mocktailContainer = MocktailContainer.getInstance();
//        mocktailContainer.clean();
//        DirectFieldAccessor dfa = new DirectFieldAccessor(mocktailContainer);
//        // Need to set as Mocktail Context is a singleton class and is getting
//        // set-upped from multiple places
//        dfa.setPropertyValue("recordingDirectory", "");
//
//        String testAspectsDirectory = absolutePath("src", "test", "resources",
//                "org", "mocktail", "aspectsDirectory");
//        MocktailAspectsCreator.INSTANCE.createAspects(
//                MocktailObjectMother.getMocktailsForAspects(), new File(
//                        testAspectsDirectory), MocktailMode.RECORDING_MODE);
//
//        String firstMocktailFile = absolutePath("src", "test", "resources",
//                "org", "mocktail", "aspectsDirectory", "org", "mocktail", "aj",
//                "creator", "RecorderAspectTemplateProcesser.aj");
//        assertTrue(new File(firstMocktailFile).isFile());
//
//        String secondMocktailFile = absolutePath("src", "test", "resources",
//                "org", "mocktail", "aspectsDirectory", "org", "mocktail",
//                "xml", "domain", "RecorderAspectMocktail.aj");
//        assertTrue(new File(secondMocktailFile).isFile());
//        // Uncomment the deleteDir if you want to see the aspects
//        deleteDir(new File(testAspectsDirectory));
//    }

}
