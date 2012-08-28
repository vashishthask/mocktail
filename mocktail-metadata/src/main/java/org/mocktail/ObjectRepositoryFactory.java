package org.mocktail;

import org.mocktail.repository.DiskObjectRepository;
import org.mocktail.repository.ObjectRepository;
import org.mocktail.repository.YamlDiskRepository;

public class ObjectRepositoryFactory {

    public static ObjectRepository create(String serializer) {
        if("yaml".equals(serializer)){
            return new YamlDiskRepository();
        }
        return new DiskObjectRepository();
    }
}
