package telran.java51.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java51.book.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	
	List<Book> findBooksByAuthorsName(String name);
	
	List<Book> findBooksByPublisherPublisherName(String publisherName);
	
	List<Book> findAuthorsByIsbn(String isbn);
	
	List<Book> findPublishersByAuthorsName(String name);

}
