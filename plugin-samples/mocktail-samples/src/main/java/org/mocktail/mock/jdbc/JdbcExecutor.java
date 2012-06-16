package org.mocktail.mock.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcExecutor {

	Connection con = null;
	private static ResultSetParserCallback resultSetParserCallback;

	public JdbcExecutor() {
		String connectionURL = "jdbc:hsqldb:mem:mypersistence;user=sa";
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			con = DriverManager.getConnection(connectionURL);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List executeQuery(String query) {
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return resultSetParserCallback.parse(resultSet);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean execute(String query) {
		try {
			Statement statement = con.createStatement();
			boolean queryExecuted = statement.execute(query);
			statement.close();
			return queryExecuted;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void setResultParserCallback(
			ResultSetParserCallback resultSetParserCallback) {
		JdbcExecutor.resultSetParserCallback = resultSetParserCallback;

	}

}
