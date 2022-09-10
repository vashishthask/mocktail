package in.malonus.tcpcache;

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

import org.slf4j.LoggerFactory;

public class ObjectFileOperations {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ObjectFileOperations.class);

    public boolean fileAlreadyExists(String objectId, String location) {
        LOGGER.debug("ObjectFileOperations.fileAlreadyExists: The file location is:" + location + " objectId is:"
                + objectId);
        File objectFile = new File(location, objectId);
        return objectFile.exists();
    }

    public boolean isRecordingAvailable(String location) {
        File directory = new File(location);
        String[] files = directory.list();
        if (files != null && files.length > 0) {
            return true;
        }
        return false;
    }

    public void saveObjectInFile(Object object, String objectId, String location) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(location, objectId)));
            objectOutputStream.writeObject(object);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (IOException e) {
            LOGGER.error("IOException occured", e);
        } finally {
            if (null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Exception while closing outputstream", e);
                }
            }
        }
    }

    public void saveObjectInFile(Object object, String fileName, File parentFolder) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(parentFolder, fileName)));
            objectOutputStream.writeObject(object);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (IOException e) {
            LOGGER.error("IOException occured", e);
        } finally {
            if (null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Exception while closing outputstream", e);
                }
            }
        }
    }

    public void writeStringToFile(String yamlStr, String objectId, String location) {
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(new File(location, objectId)));
            printStream.print(yamlStr);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } finally {
            if (null != printStream) {
                printStream.close();
            }
        }
    }

    public Object getObjectFromFile(String objectId, String location) {
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new FileInputStream(new File(location, objectId)));
            return is.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (IOException e) {
            LOGGER.error("IOException occurred", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("Exception while closing outputstream", e);
                }
            }
        }
        return null;
    }

    public Object getObjectFromFile(String fileName, File parentFolder) {
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new FileInputStream(new File(parentFolder, fileName)));
            return is.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("Exception while closing outputInputstream", e);
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
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException", e);
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    LOGGER.error("Exception while closing stream", e);
                }
            }
        }
        return null;
    }

    public boolean deleteObject(String objectId, String location) {
        File file = new File(location, objectId);
        return file.delete();
    }

}
