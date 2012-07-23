package org.mocktail.mock.jdbc;

import java.util.List;

public interface GenericJDBCDao<E> {
	int save(E e);
	E get(Long id);
	int delete(E e);
	List<E> find(String query);
	int update(E e);
}
