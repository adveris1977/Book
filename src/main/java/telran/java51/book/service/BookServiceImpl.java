package telran.java51.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dao.AuthorRepository;
import telran.java51.book.dao.BookRepository;
import telran.java51.book.dao.PublisherRepository;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.dto.PublisherDto;
import telran.java51.book.dto.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final PublisherRepository publisherRepository;
	final AuthorRepository authorRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher (bookDto.getPublisher())));		
		// Authors
		Set<Author> authors = bookDto.getAuthors().stream()
						.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
						.collect(Collectors.toSet());	
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
//		Publisher publisher = book.getPublisher();
		bookRepository.delete(book);
//		publisher.getPublisherName().del
//		Set<Author> authors = book.getAuthors();
		
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto updateBook(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		Publisher publisher = book.getPublisher();
		Set<Author> authors = book.getAuthors();
		book.setTitle(title);
		bookRepository.save(book);
		publisherRepository.save(publisher);
		for (Author author : authors) {
	        authorRepository.save(author);
		}
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> findBooksByAuthor(String name) {
		return bookRepository.findBooksByAuthorsName(name).stream()
				.map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
				
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
		return bookRepository.findBooksByPublisherPublisherName(publisherName).stream()
				.map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
	}
//fix
	@Transactional(readOnly = true)
	@Override
	public Iterable<AuthorDto> findAuthorsByBook(String isbn) {
		return bookRepository.findAuthorsByIsbn(isbn).stream()
				.map(book -> modelMapper.map(book, AuthorDto.class))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<PublisherDto> findPublishersByAuthor(String name) {
		return bookRepository.findPublishersByAuthorsName(name).stream()
				.map(book -> modelMapper.map(book, PublisherDto.class))
				.collect(Collectors.toList());
	}

}
