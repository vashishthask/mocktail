package in.malonus.tcpcache;

import java.io.File;

public class CacheFileInfo {

    private Configuration config;

    public CacheFileInfo(Configuration config) {
        this.config = config;
    }

    public String getObjectId() {
        String className = config.getTestClassName();
        String objectId = className.substring(className.lastIndexOf(".") + 1) + "." + config.getTestMethodName();
        return objectId;
    }

    public String getCacheFileLocation() {
        String className = config.getTestClassName();
        String packageName = className.substring(0, className.lastIndexOf("."));

        String location = System.getProperty("user.dir") + File.separator + config.getCacheLoation() + File.separator
                + packageName.replace(".", File.separator);
        return location;
    }

}
