package org.mocktail.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.yaml.snakeyaml.Yaml;

public class YamlDiskRepository implements ObjectRepository {

    @Override
    public void saveObject(Object objectToBeSerialized,
            OutputStream outputStream) throws IOException {
        Yaml yaml = new Yaml();
        String yamlStr = yaml.dump(objectToBeSerialized);
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(yamlStr);
    }

    @Override
    public Object getObject(InputStream inputStream) throws IOException,
            ClassNotFoundException {
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }

    @Override
    public void saveObject(Object object, String objectId, String location) {
        ObjectOutputStream objectOutputStream = null;
        try {
            Yaml yaml = new Yaml();
            String yamlStr = yaml.dump(object);
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                    new File(location, objectId)));
            objectOutputStream.writeObject(yamlStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != objectOutputStream)
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    @Override
    public Object getObject(String objectId, String location) {
        try {
            FileInputStream yamlInputStream = new FileInputStream(new File(location,
                    objectId));
            return new Yaml().load(yamlInputStream);
        } catch (FileNotFoundException e) {
             e.printStackTrace();
        } 
        return null;
    }

    @Override
    public boolean objectAlreadyExist(String objectId, String location) {
        File objectFile = new File(location, objectId);
        return objectFile.exists();
    }
}
