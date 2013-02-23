package com.svashishtha.mocktail.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class DiskObjectRepository implements ObjectRepository {
    private ObjectFileOperations fileOperations = new ObjectFileOperations();

    public void saveObject(Object objectToBeSerialized,
            OutputStream outputStream) throws IOException {
        new ObjectOutputStream(outputStream).writeObject(objectToBeSerialized);
    }

    public Object getObject(InputStream inputStream) throws IOException,
            ClassNotFoundException {
        return new ObjectInputStream(inputStream).readObject();
    }

    public boolean objectAlreadyExist(String objectId, String location) {
        return fileOperations.fileAlreadyExists(objectId, location);
    }

    public void saveObject(Object object, String objectId, String location) {
        fileOperations.saveObjectInFile(object, objectId, location);
    }

    public Object getObject(String objectId, String location) {
        return fileOperations.getObjectFromFile(objectId, location);
    }

	@Override
	public boolean clearObjectIfAvailable(String objectId, String location) {
		boolean objectAlreadyExist = objectAlreadyExist(objectId, location);
		if(objectAlreadyExist){
			return fileOperations.deleteObject(objectId, location);
		}
		return true;
	}
    
    

}
