package org.mocktail.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectRepository {

    public void saveObject(Object objectToBeSerialized,
            OutputStream outputStream) throws IOException;

    public Object getObject(InputStream inputStream) throws IOException,
            ClassNotFoundException;

    public void saveObject(Object object, String objectId, String location);

    public Object getObject(String objectId, String location);
    
    public boolean clearObjectIfAvailable(String objectId, String location);

    public boolean objectAlreadyExist(String objectId, String location);
}
