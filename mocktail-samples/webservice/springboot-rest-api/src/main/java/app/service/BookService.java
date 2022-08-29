package app.service;

import java.util.List;

import app.model.Book;

public interface BookService {

	Book saveBook(Book Book);

	List<Book> fetchBookList();

	Book updateBook(Book Book, String BookId);

	void deleteBookById(String BookId);

	Book findBookById(String bookId);
}
