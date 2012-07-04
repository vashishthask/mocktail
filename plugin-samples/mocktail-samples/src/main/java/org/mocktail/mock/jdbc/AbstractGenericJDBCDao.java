package org.mocktail.mock.jdbc;

import java.sql.ResultSet;
import java.util.List;

public abstract class AbstractGenericJDBCDao<E> implements GenericJDBCDao<E> {

	public E get(Long id) {
		JdbcExecutor jdbcExecutor = new JdbcExecutor();
		ResultSet entityLoadResultSet = jdbcExecutor
				.getResultSet(getLoadQuery(id));
		E entity = mapResultSetToEntity(entityLoadResultSet);
		return entity;
	}

	public void delete(E e) {
	}

	public List<E> find(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(E e) {

	}

	protected abstract String getLoadQuery(Long id);

	protected abstract E mapResultSetToEntity(ResultSet resultSet);

}
