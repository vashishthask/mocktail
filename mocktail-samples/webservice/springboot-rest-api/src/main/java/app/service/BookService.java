package app.service;

import java.util.List;

import app.model.Book;

public interface BookService {

	Book saveBook(Book book);

	List<Book> fetchBookList();

	Book updateBook(Book book, Long bookId);

	void deleteBookById(Long bookId);

	Book findBookById(Long bookId);
}
