package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.model.Book;
import app.service.BookService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
	@LocalServerPort
	private int port;

	// Required to Generate JSON content from Java objects
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String HOST_PORT = "http://localhost:";

	// Required to delete the data added for tests.
	// Directly invoke the APIs interacting with the DB
	@Autowired
	private BookService bookService;

	// Test RestTemplate to invoke the APIs.
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testCreateBookApi() throws JsonProcessingException {

		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", "Book 1");
		requestBody.put("isbn", "QWER1234");
		requestBody.put("author", "Author 1");
		requestBody.put("pages", 200);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		// Creating http entity object with request body and headers
		HttpEntity<String> httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
				requestHeaders);

		// Invoking the API
		Map<String, Object> apiResponse = restTemplate.postForObject(HOST_PORT + port + "/book", httpEntity, Map.class,
				Collections.EMPTY_MAP);

		assertNotNull(apiResponse);

		// Asserting the response of the API.
		String message = apiResponse.get("message").toString();
		assertEquals("Book created successfully", message);
		String bookId = ((Map<String, Object>) apiResponse.get("book")).get("id").toString();

		assertNotNull(bookId);

		// Fetching the Book details directly from the DB to verify the API succeeded
		Book bookFromService = bookService.findBookById(bookId);
		assertEquals("Book 1", bookFromService.getName());
		assertEquals("QWER1234", bookFromService.getIsbn());
		assertEquals("Author 1", bookFromService.getAuthor());
		assertTrue(200 == bookFromService.getPages());

		// Delete the data added for testing
		bookService.deleteBookById(bookId);

	}

	@Test
	public void testGetBookDetailsApi() {
		// Create a new book using the BookRepository API
		Book book = new Book("Book1", "�SBN1", "Author1", 200);
		bookService.saveBook(book);

		String bookId = book.getId();

		// Now make a call to the API to get details of the book
		Book apiResponse = restTemplate.getForObject(HOST_PORT + port + "/book/" + bookId, Book.class);

		// Verify that the data from the API and data saved in the DB are same
		assertNotNull(apiResponse);
		assertEquals(book.getName(), apiResponse.getName());
		assertEquals(book.getId(), apiResponse.getId());
		assertEquals(book.getIsbn(), apiResponse.getIsbn());
		assertEquals(book.getAuthor(), apiResponse.getAuthor());
		assertTrue(book.getPages() == apiResponse.getPages());

		// Delete the Test data created
		bookService.deleteBookById(bookId);
	}

	@Test
	public void testUpdateBookDetails() throws JsonProcessingException {
		// Create a new book using the BookRepository API
		Book book = new Book("Book1", "ISBN1", "Author1", 200);
		bookService.saveBook(book);

		String bookId = book.getId();

		// Now create Request body with the updated Book Data.
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", "Book2");
		requestBody.put("isbn", "ISBN2");
		requestBody.put("author", "Author2");
		requestBody.put("pages", 200);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		// Creating http entity object with request body and headers
		HttpEntity<String> httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
				requestHeaders);

		// Invoking the API
		Map<String, Object> apiResponse = (Map<String, Object>) restTemplate
				.exchange(HOST_PORT + port + "/book/" + bookId, HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP)
				.getBody();

		assertNotNull(apiResponse);
		assertTrue(!apiResponse.isEmpty());

		// Asserting the response of the API.
		String message = apiResponse.get("message").toString();
		assertEquals("Book Updated successfully", message);

		// Fetching the Book details directly from the DB to verify the API succeeded in
		// updating the book details
		Book bookFromDb = bookService.findBookById(bookId);
		assertNotNull(bookFromDb);
		assertEquals(requestBody.get("name"), bookFromDb.getName());
		assertEquals(requestBody.get("isbn"), bookFromDb.getIsbn());
		assertEquals(requestBody.get("author"), bookFromDb.getAuthor());
		assertTrue(Integer.parseInt(requestBody.get("pages").toString()) == bookFromDb.getPages());

		// Delete the data added for testing
		bookService.deleteBookById(bookId);

	}

	@Test
	public void testDeleteBookApi() {
		// Create a new book using the BookRepository API
		Book book = new Book("Book1", "ISBN1", "Author1", 200);
		bookService.saveBook(book);

		String bookId = book.getId();

		// Now Invoke the API to delete the book
		restTemplate.delete(HOST_PORT + port + "/book/" + bookId, Collections.emptyMap());

		// Try to fetch from the DB directly
		Book bookFromDb = bookService.findBookById(bookId);
		// and assert that there is no data found
		assertNull(bookFromDb);
	}

	@Test
	public void testGetAllBooksApi() {
		// Add some test data for the API
		Book book1 = new Book("Book1", "ISBN1", "Author1", 200);
		bookService.saveBook(book1);

		Book book2 = new Book("Book2", "ISBN2", "Author2", 200);
		bookService.saveBook(book2);

		// Invoke the API
		Map<String, Object> apiResponse = restTemplate.getForObject(HOST_PORT + port + "/book", Map.class);

		// Assert the response from the API
		int totalBooks = Integer.parseInt(apiResponse.get("totalBooks").toString());
		assertTrue(totalBooks == 2);

		List<Map<String, Object>> booksList = (List<Map<String, Object>>) apiResponse.get("books");
		assertTrue(booksList.size() == 2);

		// Delete the test data created
		bookService.deleteBookById(book1.getId());
		bookService.deleteBookById(book2.getId());
	}
}
