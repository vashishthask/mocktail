package org.mocktail;

import org.mocktail.repository.DiskObjectRepository;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.repository.YamlDiskRepository;

public class ObjectRepositoryFactory {

    public static ObjectRepository create(String serializerType) {
        if("yaml".equals(serializerType)){
            return new YamlDiskRepository();
        }
        return new DiskObjectRepository();
    }
    
    public static ObjectRepository create() {
        return new DiskObjectRepository();
    }
}
