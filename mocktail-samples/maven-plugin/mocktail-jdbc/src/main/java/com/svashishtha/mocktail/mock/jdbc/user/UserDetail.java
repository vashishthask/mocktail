package com.svashishtha.mocktail.mock.jdbc.user;

import java.io.Serializable;

public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
