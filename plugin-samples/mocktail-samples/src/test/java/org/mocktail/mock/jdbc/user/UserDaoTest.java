package org.mocktail.mock.jdbc.user;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mocktail.mock.jdbc.JdbcExecutor;

public class UserDaoTest {

	private JdbcExecutor jdbcExecutor;
	UserDao userDao;

	@Before
	public void setup() {
	    userDao = new UserDao();
		jdbcExecutor = new JdbcExecutor();
		jdbcExecutor.execute("CREATE TABLE USERDETAIL (id INTEGER,age INTEGER)");
		jdbcExecutor.execute("insert into USERDETAIL values (1,10)");
		
	}
	
	@Test
	public void shouldGetUser() {
		
		//search with recording mode
        UserDetail userDetail = userDao.get(1L);
        assertNotNull(userDetail);
        assertThat(10, is(userDetail.getAge()));
        
        
        //update userdetail record with new value
        jdbcExecutor.execute("update userdetail set age=12 where id=1");
        
        //search again
        UserDetail recordedUserDetail = userDao.get(1L);
        assertEquals(10, recordedUserDetail.getAge());
	}
}
