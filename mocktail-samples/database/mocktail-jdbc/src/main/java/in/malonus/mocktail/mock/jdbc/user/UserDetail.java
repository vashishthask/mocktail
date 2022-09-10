package in.malonus.mocktail.mock.jdbc.user;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

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
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return ToStringBuilder.reflectionToString(this);
    }
}
