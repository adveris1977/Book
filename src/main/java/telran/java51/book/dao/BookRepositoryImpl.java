package telran.java51.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Book> findByAuthorsName(String name) {
		TypedQuery<Book> query = em.createQuery("Select b from Book b joib b.authors a where a.name =: authorName", Book.class);
		List<Book> books = query.getResultList();
		return books.stream();
	}

	@Override
	public Stream<Book> findByPublisherPublisherName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByAuthorsName(String name) {
		em.remove(em.find(Author.class, name));

	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(em.find(Book.class, isbn));
	}

	@Override
	public void deleteById(String isbn) {
		em.remove(em.find(Book.class, isbn));

	}

}
