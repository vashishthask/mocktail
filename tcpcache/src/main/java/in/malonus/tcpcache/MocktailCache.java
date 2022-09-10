package in.malonus.tcpcache;

import java.io.File;

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
        CacheMode cacheMode = config.getCacheMode();
        if(CacheMode.RECORDING_NEW.equals(cacheMode))
            clearObjectOnDisk(config.getTestClassName());
        if(CacheMode.RECORDING == cacheMode || cacheMode == CacheMode.RECORDING_NEW){
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
        DiskObjectRepository objectRepository = new DiskObjectRepository();
        objectRepository.saveObject(response, objectId, location);
    }

    private void clearObjectOnDisk(String objectId2) {
        String location = cacheFileInfo.getCacheFileLocation();
        String objectId = cacheFileInfo.getObjectId();
        
        DiskObjectRepository objectRepository = new DiskObjectRepository();
        objectRepository.clearObjectIfAvailable(objectId, location);
    }


}
