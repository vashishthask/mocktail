package in.malonus.mocktail.metadata.aj.creator;

import java.io.File;
import java.util.List;


import in.malonus.mocktail.MocktailMode;
import in.malonus.mocktail.metadata.xml.domain.AspectType;
import in.malonus.mocktail.metadata.xml.domain.Mocktail;

public class MocktailAspectsCreator {
    
    public void createRecordingAspects(List<Mocktail> mocktails,
            File aspectsDirectory) {
        createAspects(mocktails, aspectsDirectory, MocktailMode.RECORDING);
        
    }

    public void createPlaybackAspects(List<Mocktail> mocktails,
            File aspectsDirectory) {
        createAspects(mocktails, aspectsDirectory, MocktailMode.PLAYBACK);
        
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