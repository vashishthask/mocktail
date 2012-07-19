package org.mocktail.mock.jdbc;

import java.sql.ResultSet;
import java.util.List;

import org.mocktail.mock.jdbc.user.UserDetail;

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
		return null;
	}

	public void save(E e) {

	}

	protected abstract String getLoadQuery(Long id);

	protected abstract E mapResultSetToEntity(ResultSet resultSet);

    public void update(UserDetail userDetail) {
        
    }

}
