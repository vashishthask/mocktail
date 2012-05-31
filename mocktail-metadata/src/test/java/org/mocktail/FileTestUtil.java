package org.mocktail;

import java.io.File;
import java.io.IOException;

public class FileTestUtil {

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    public static String absolutePath(String... path) throws IOException {
        File file = new File(".");
        StringBuffer absolutePath = new StringBuffer(file.getCanonicalPath());
        for (String pathElement : path) {
            absolutePath.append(File.separator).append(pathElement);
        }
        return absolutePath.toString();
    }
}
