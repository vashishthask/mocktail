package com.xebia.smok.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public enum RecordingsRepository {

	SERIALIZER_RECORDINGS_REPOSITORY;

	public void marshall(Object objectToBeSerialized, OutputStream outputStream)
			throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(outputStream);
		os.writeObject(objectToBeSerialized);
	}

	public Object unmarshall(InputStream inputStream) throws IOException,
			ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(inputStream);
		return is.readObject();
	}

}
