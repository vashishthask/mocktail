package org.mocktail.mock.jdbc;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetParserCallback {

	public List parse(ResultSet resultSet);

}
