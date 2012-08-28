package org.mocktail.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ObjectFileOperations {

    public boolean fileAlreadyExists(String objectId, String location) {
        File objectFile = new File(location, objectId);
        return objectFile.exists();
    }

    public void saveObjectInFile(Object object, String objectId, String location) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                    new File(location, objectId)));
            objectOutputStream.writeObject(object);
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

    public void writeStringToFile(String yamlStr, String objectId,
            String location) {
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(new File(
                    location, objectId)));
            printStream.print(yamlStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != printStream) {
                printStream.close();
            }
        }
    }

    public Object getObjectFromFile(String objectId, String location) {
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new FileInputStream(new File(location,
                    objectId)));
            return is.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getStringContentFromFile(String objectId, String location) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(location, objectId));

            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
