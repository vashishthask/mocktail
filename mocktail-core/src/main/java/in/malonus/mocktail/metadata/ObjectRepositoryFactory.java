package in.malonus.mocktail.metadata;

import in.malonus.mocktail.repository.DiskObjectRepository;
import in.malonus.mocktail.repository.ObjectRepository;
import in.malonus.mocktail.repository.YamlDiskRepository;

public class ObjectRepositoryFactory {

    public static ObjectRepository create(String serializerType) {
        if ("yaml".equals(serializerType)) {
            return new YamlDiskRepository();
        }
        return new DiskObjectRepository();
    }

    public static ObjectRepository create() {
        return new DiskObjectRepository();
    }
}
