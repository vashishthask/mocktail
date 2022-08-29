package app.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Book;
import app.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book saveBook(Book Book) {
		return bookRepository.save(Book);
	}

	@Override
	public List<Book> fetchBookList() {
		return (List<Book>) bookRepository.findAll();
	}

	@Override
	public Book updateBook(Book Book, String BookId) {
		Book book = bookRepository.findById(BookId).get();
		if (Objects.nonNull(Book.getName()) && !"".equalsIgnoreCase(Book.getName())) {
			book.setName(Book.getName());
		}

		if (Objects.nonNull(Book.getAuthor()) && !"".equalsIgnoreCase(Book.getAuthor())) {
			book.setAuthor(Book.getAuthor());
		}
		if (Objects.nonNull(Book.getIsbn()) && !"".equalsIgnoreCase(Book.getIsbn())) {
			book.setIsbn(Book.getIsbn());
		}
		book.setPages(Book.getPages());
		return bookRepository.save(book);
	}

	@Override
	public void deleteBookById(String BookId) {
		bookRepository.deleteById(BookId);
	}

	@Override
	public Book findBookById(String bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book.isPresent())
			return book.get();
		return null;
	}

}
