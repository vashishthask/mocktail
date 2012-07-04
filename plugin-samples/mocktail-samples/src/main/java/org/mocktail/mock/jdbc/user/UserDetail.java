package org.mocktail.mock.jdbc.user;

import java.io.Serializable;

public class UserDetail implements Serializable{

	private Long id;
	private int age;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
