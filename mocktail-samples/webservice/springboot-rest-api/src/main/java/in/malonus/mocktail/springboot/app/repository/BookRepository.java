package in.malonus.mocktail.springboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.malonus.mocktail.springboot.app.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
