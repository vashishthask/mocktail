package org.mocktail.mock.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class JdbcExecutorTest {

	private JdbcExecutor jdbcExecutor;

	@Before
	public void setup() {
		jdbcExecutor = new JdbcExecutor();
		boolean execute = jdbcExecutor.execute("CREATE TABLE USER (id INTEGER,age INTEGER)");
	}
	
	@Test(expected=AssertionFailedError.class)
	public void shouldGetResultsForQueryFromDatabase() {
		JdbcExecutor.setResultParserCallback(new ResultSetParserCallback() {
			
			public List<Long> parse(ResultSet resultSet) {
				List<Long> ids = new ArrayList<Long>();
				try {
					while(resultSet.next()){
						long userId = resultSet.getLong(0);
						ids.add(userId);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ids;
			}
		});
		List<Long> usersList = jdbcExecutor.executeQuery("select id from USER");
		assertNotNull(usersList);
		assertEquals(4, usersList.size());
	}

}
