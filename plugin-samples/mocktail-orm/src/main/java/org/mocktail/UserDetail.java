package org.mocktail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDetail {

<<<<<<< HEAD
	
	@Id @GeneratedValue @Column(name = "MESSAGE_ID")
	private Long id;
=======
    @Id
    @GeneratedValue
    @Column(name = "MESSAGE_ID")
    private Long id;
>>>>>>> dc038b3d24a102aa117552e10a66b0a3a77b5284

    @Column
    private String name;

<<<<<<< HEAD
	public UserDetail() {
	}
	
	public UserDetail(String name) {
		this.name = name;
	}
=======
    public UserDetail(String name) {
        this.name = name;
    }
>>>>>>> dc038b3d24a102aa117552e10a66b0a3a77b5284

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

<<<<<<< HEAD
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
=======
    public String getText() {
        return name;
    }

    public void setText(String name) {
        this.name = name;
    }

>>>>>>> dc038b3d24a102aa117552e10a66b0a3a77b5284
}
