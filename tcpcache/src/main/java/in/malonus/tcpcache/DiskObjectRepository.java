package in.malonus.tcpcache;

public class DiskObjectRepository {
    private ObjectFileOperations fileOperations = new ObjectFileOperations();

    public boolean objectAlreadyExist(String objectId, String location) {
        return fileOperations.fileAlreadyExists(objectId, location);
    }

    public void saveObject(Object object, String objectId, String location) {
        fileOperations.saveObjectInFile(object, objectId, location);
    }

    public Object getObject(String objectId, String location) {
        return fileOperations.getObjectFromFile(objectId, location);
    }

    public boolean clearObjectIfAvailable(String objectId, String location) {
        boolean objectAlreadyExist = objectAlreadyExist(objectId, location);
        if (objectAlreadyExist) {
            return fileOperations.deleteObject(objectId, location);
        }
        return true;
    }
}
