package com.svashishtha.mocktail.samples.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDetail {

	@Id
	@GeneratedValue
	@Column(name = "MESSAGE_ID")
	private Long id;

	@Column
	private String name;

	public UserDetail() {
	}

	public UserDetail(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
