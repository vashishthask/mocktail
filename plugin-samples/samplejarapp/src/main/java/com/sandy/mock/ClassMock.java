package com.sandy.mock;

public class ClassMock {

    public int getIntValue() {
        return 10;
    }

    public String getStrValue() {
        return "string value";
    }

    public User getObjectValue() {
        return new User(1, "sandy");
    }
}
