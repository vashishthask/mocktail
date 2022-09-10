package in.malonus.mocktail.springboot.app.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.malonus.mocktail.springboot.app.model.Book;
import in.malonus.mocktail.springboot.app.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> fetchBookList() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book bookData, Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        if (Objects.nonNull(bookData.getName()) && !"".equalsIgnoreCase(bookData.getName())) {
            book.setName(bookData.getName());
        }

        if (Objects.nonNull(bookData.getAuthor()) && !"".equalsIgnoreCase(bookData.getAuthor())) {
            book.setAuthor(bookData.getAuthor());
        }
        if (Objects.nonNull(bookData.getIsbn()) && !"".equalsIgnoreCase(bookData.getIsbn())) {
            book.setIsbn(bookData.getIsbn());
        }
        book.setPages(bookData.getPages());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book findBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent())
            return book.get();
        return null;
    }

}
