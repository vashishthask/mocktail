package in.malonus.mocktail.mock.jdbc;

import java.util.List;

public interface GenericDao<E> {
    int save(E e);

    E get(Long id);

    int delete(E e);

    List<E> find(String query);

    List<E> getAll();

    int update(E e);
}
