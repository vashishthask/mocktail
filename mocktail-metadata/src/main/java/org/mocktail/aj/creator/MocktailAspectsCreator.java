package org.mocktail.aj.creator;

import java.io.File;
import java.util.List;

import org.mocktail.xml.domain.AspectType;
import org.mocktail.xml.domain.Mocktail;
import org.mocktail.xml.domain.MocktailMode;

public class MocktailAspectsCreator {
    
    public void createRecordingAspects(List<Mocktail> mocktails,
            File aspectsDirectory) {
        createAspects(mocktails, aspectsDirectory, MocktailMode.RECORDING_MODE);
        
    }

    public void createPlaybackAspects(List<Mocktail> mocktails,
            File aspectsDirectory) {
        createAspects(mocktails, aspectsDirectory, MocktailMode.PLAYBACK_MODE);
        
    }
    
    public void createClassAspect(Mocktail mocktail, File aspectsRootDirectory, MocktailMode mocktailMode) {
        createAspect(mocktail, aspectsRootDirectory, AspectType.CLASS_ASPECT_TYPE, mocktailMode);
    }
    
    public void createMethodAspect(Mocktail mocktail, File aspectsRootDirectory, MocktailMode mocktailMode) {
        createAspect(mocktail, aspectsRootDirectory, AspectType.METHODS_ASPECT_TYPE, mocktailMode);
    }
    
    private void createAspects(List<Mocktail> mocktailsForAspects,
            File aspectsDirectory, MocktailMode mocktailMode) {
        for (Mocktail mocktail : mocktailsForAspects) {
            if(mocktail.onlyForClass()){
                createClassAspect(mocktail, aspectsDirectory, mocktailMode);
            } else{
                createMethodAspect(mocktail, aspectsDirectory, mocktailMode);
            }
        }
    }

    private void createAspect(Mocktail mocktail, File aspectsRootDirectory, AspectType aspectType, MocktailMode mocktailMode) {
        MocktailTemplateProcesser mocktailTemplateProcessor = new MocktailTemplateProcesser();
        mocktailTemplateProcessor.createAspect(mocktail, aspectType, mocktailMode, aspectsRootDirectory);
    }
}