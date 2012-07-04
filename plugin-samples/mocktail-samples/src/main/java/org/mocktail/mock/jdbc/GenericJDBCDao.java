package org.mocktail.mock.jdbc;

import java.util.List;

public interface GenericJDBCDao<E> {
	
	void save(E e);
	E get(Long id);
	void delete(E e);
	List<E> find(String query);

}
