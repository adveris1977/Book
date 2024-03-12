package telran.java51.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.dto.PublisherDto;
import telran.java51.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {
	final BookService bookService;
	
	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}
	
	@GetMapping("/book/{isbn}")
	public BookDto findBookByIsbn(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
	}
	
	@DeleteMapping("/book/{id}")
	public BookDto removeBook(@PathVariable("id") String isbn) {
		return bookService.removeBook(isbn);
	}

	@PutMapping("/book/{id}/title/{title}")
	public BookDto updateBook(@PathVariable("id") String isbn, @PathVariable String title) {
		return bookService.updateBook(isbn, title);
	}
	
	@GetMapping("/books/author/{author}")
	public Iterable<BookDto> findBooksByAuthor(@PathVariable("author") String name){
		return bookService.findBooksByAuthor(name);
	}
	
	@GetMapping("/books/publisher/{publisher}")
	public Iterable<BookDto> findBooksByPublisher(@PathVariable("publisher") String publisherName){
		return bookService.findBooksByPublisher(publisherName);
	}
	@GetMapping("/authors/book/{isbn}")
	public Iterable<AuthorDto> findAuthorsByBook(@PathVariable String isbn){
		return bookService.findAuthorsByBook(isbn);
	}
	@GetMapping("publishers/author/{author}")
	public Iterable<PublisherDto> findPublishersByAuthor(@PathVariable("author") String name){
		return bookService.findPublishersByAuthor(name);
	}
}
