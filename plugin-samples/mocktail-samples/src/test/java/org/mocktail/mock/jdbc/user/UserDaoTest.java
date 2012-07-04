package org.mocktail.mock.jdbc.user;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.mocktail.mock.jdbc.JdbcExecutor;

public class UserDaoTest {

	private JdbcExecutor jdbcExecutor;

	@Before
	public void setup() {
		jdbcExecutor = new JdbcExecutor();
		boolean execute = jdbcExecutor.execute("CREATE TABLE USERDETAIL (id INTEGER,age INTEGER)");
		execute = jdbcExecutor.execute("insert into USERDETAIL values (1,10)");
		
	}
	
	@Test
	public void shouldGetUser() {
		UserDao userDao = new UserDao();
		UserDetail userDetail = userDao.get(1L);
		assertNotNull(userDetail);
		assertThat(1L, is(userDetail.getId()));
	}

}
