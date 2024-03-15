package telran.java51.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import telran.java51.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<String> findByPublishersAuthor(String authorName) {
		TypedQuery<String> query = em.createQuery("select distinct p.publisherName from Book b join b.authors a join b.publisher p where a.name=: authorName", String.class);
		List<String> publishers = query.getResultList();
		return publishers;
	}

	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		TypedQuery<Publisher> query = em.createQuery("select distinct p.publisherName from Book b join b.authors a join b.publisher p where a.name=: authorName", Publisher.class);
		List<Publisher> publishers = query.getResultList();
		return publishers.stream();
	}

	@Override
	public Optional<Publisher> findById(String publisher) {
		return Optional.ofNullable(em.find(Publisher.class, publisher));
	}

	@Override
	//@Transactional
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
	//	em.merge(publisher);
		return publisher;
	}

}
