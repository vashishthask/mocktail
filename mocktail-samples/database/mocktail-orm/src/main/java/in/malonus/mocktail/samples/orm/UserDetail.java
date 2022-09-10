package in.malonus.mocktail.samples.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserDetail {

	@Id
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
