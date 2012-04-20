package org.mocktail;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileUtilTest {

    @Test
    public void testReplaceFilePath() {
        String packageName = "com.mocktail.aj.creator";
        packageName = packageName.replace(".", "\\");
        assertEquals("com\\mocktail\\aj\\creator", packageName);
    }
}
