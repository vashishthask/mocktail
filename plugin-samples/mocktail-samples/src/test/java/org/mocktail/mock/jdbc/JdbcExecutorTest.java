package org.mocktail.mock.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JdbcExecutorTest {

	private JdbcExecutor jdbcExecutor;

	@Before
	public void setup() {
		jdbcExecutor = new JdbcExecutor();
		try {
			
			boolean execute = jdbcExecutor.execute("CREATE TABLE USER (Nr INTEGER,Name INTEGER)");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldGetResultsForQuery() {
		JdbcExecutor.setResultParserCallback(new ResultSetParserCallback() {
			
			public List parse(ResultSet resultSet) {
				ArrayList usersList = new ArrayList();
				usersList.add(1L);
				usersList.add(2L);
				/*usersList.add(2L);
				usersList.add(2L);*/
				return usersList;
			}
		});
		List usersList = jdbcExecutor.executeQuery("select * from USER");
		assertNotNull(usersList);
		assertEquals(2, usersList.size());
	}

}
