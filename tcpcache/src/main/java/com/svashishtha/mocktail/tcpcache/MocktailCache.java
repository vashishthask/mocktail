package com.svashishtha.mocktail.tcpcache;

import java.io.File;

import com.svashishtha.mocktail.MocktailMode;
import com.svashishtha.mocktail.repository.DiskObjectRepository;
import com.svashishtha.mocktail.repository.ObjectRepository;

public class MocktailCache {
    
    private Configuration config;
    private CacheFileInfo cacheFileInfo;
    private String response;

    public MocktailCache(Configuration config, String response){
        this.config = config;
        this.cacheFileInfo = new CacheFileInfo(config);
        this.response = response;
    }
    
    public void saveInMocktailRepository() {
        MocktailMode mocktailMode = config.getMocktailMode();
        if(MocktailMode.RECORDING_NEW.equals(mocktailMode))
            clearObjectOnDisk(config.getTestClassName());
        if(MocktailMode.RECORDING == mocktailMode || mocktailMode == MocktailMode.RECORDING_NEW){
            saveObjectInRepository();
        }
    }

    private void saveObjectInRepository() {
        String location = cacheFileInfo.getCacheFileLocation();
        String objectId = cacheFileInfo.getObjectId();

        // Verifying if root recording directory where all recordings exist is already their or not

        if (!(new File(location)).exists()) {
            (new File(location)).mkdirs();
        }
        ObjectRepository objectRepository = new DiskObjectRepository();
        objectRepository.saveObject(response, objectId, location);
    }

    private void clearObjectOnDisk(String objectId2) {
        String location = cacheFileInfo.getCacheFileLocation();
        String objectId = cacheFileInfo.getObjectId();
        
        ObjectRepository objectRepository = new DiskObjectRepository();
        objectRepository.clearObjectIfAvailable(objectId, location);
    }


}
