package org.mocktail.mock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mocktail.mock.ClassMock;
import org.mocktail.mock.User;

public class ClassMockTest {

    private ClassMock classMock;

    @Before
    public void setup() {
        classMock = new ClassMock();
    }

    @Test
    public void testIntValue() {
        assertEquals(10, classMock.getIntValue());
    }

    @Test
    public void testStringValue() {
        assertEquals("string value", classMock.getStrValue());
    }

    @Test
    public void testObjectValue() {
        assertEquals(new User(1, "sandy"), classMock.getObjectValue());
    }

}