package com.svashishtha.mocktail.repository;

public interface ObjectRepository {

    public void saveObject(Object object, String objectId, String location);

    public Object getObject(String objectId, String location);

    public boolean clearObjectIfAvailable(String objectId, String location);

    public boolean objectAlreadyExist(String objectId, String location);
}
