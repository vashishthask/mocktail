package com.xebia.smok.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class  DiskObjectRepository implements ObjectRepository{

	public void saveObject(Object objectToBeSerialized, OutputStream outputStream)
			throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(outputStream);
		os.writeObject(objectToBeSerialized);
	}

	public Object getObject(InputStream inputStream) throws IOException,
			ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(inputStream);
		return is.readObject();
	}
	
	
	public boolean objectAlreadyExist(String objectId, String location){
		File objectFile = new File(location, objectId);
		return objectFile.exists();
	}
	
	public void saveObject(Object object, String objectId, String location) {
		ObjectOutputStream objectOutputStream =  null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(location, objectId)));
			objectOutputStream.writeObject(object);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(null != objectOutputStream)
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public Object getObject(String objectId, String location) {

		ObjectInputStream is;
		try {
			is = new ObjectInputStream(new FileInputStream(new File(location, objectId)));
			return is.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
	}

}
