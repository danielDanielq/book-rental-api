package com.bookrental.app.service;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.bookdto.CreateBookRequest;
import com.bookrental.app.dto.bookdto.UpdateBookRequest;
import com.bookrental.app.entity.Author;
import com.bookrental.app.entity.Book;
import com.bookrental.app.entity.Publisher;
import com.bookrental.app.enums.BookGenre;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.mapper.BookMapper;
import com.bookrental.app.repository.AuthorRepository;
import com.bookrental.app.repository.BookRepository;
import com.bookrental.app.repository.PublisherRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public BookSimpleResponse createBook(Long authorId, Long publisherId, CreateBookRequest createBookRequest) {
        Author author = authorRepository.findById(authorId).orElseThrow(
                () -> new ResouceNotFoundException("Author not found") // Note: Fail-Fast
        );

        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(
                () -> new ResouceNotFoundException("Publisher not found") // Note: Fail-Fast
        );

        Book book = BookMapper.toEntity(createBookRequest);
        author.addBook(book);
        publisher.addBook(book);

        Book savedBook = bookRepository.save(book);
        return BookMapper.toSimpleResponse(savedBook);
    }

    public BookSimpleResponse getBookById(Long id) {
        Book bookToFind = bookRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The book is missing from the database")// Note: Fail-Fast
        );

        return BookMapper.toSimpleResponse(bookToFind);
    }

    public Page<BookSimpleResponse> searchBooks(String title, String isbn, Integer publishedYear, BookGenre genre, String authorFirstName, String authorLastName, String publisherName, int page, int size, String sort) { // Note: page number and the entities size is mandatory (to have them non-null);
        Book probeBook = new Book();

        probeBook.setTitle(title);
        probeBook.setIsbn(isbn);
        probeBook.setPublishedYear(publishedYear);
        probeBook.setGenre(genre);

        if (authorFirstName != null || authorLastName != null || !authorFirstName.isEmpty() || !authorLastName.isEmpty()) {
            Author probeAuthor = new Author();
            probeAuthor.setFirstName(authorFirstName);
            probeAuthor.setLastName(authorLastName);
            probeBook.setAuthor(probeAuthor);
        }

        if (publisherName != null || !publisherName.isEmpty()) {
            Publisher probePublisher = new Publisher();
            probePublisher.setName(publisherName);
            probeBook.setPublisher(probePublisher);
        }

        ExampleMatcher matcher = ExampleMatcher.matching() // Note: Using this example matcher helps to search within the data containing the request params;
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Book> example = Example.of(probeBook, matcher); // Note: Getting the rules and probe in one single example (parameter);

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort)); // Note: this is the page request;
        Page<Book> bookPage = bookRepository.findAll(example, pageRequest); // Note: asking the db for this exact page which contains a list of the entities;
                                                                            // Note: searching by the example that contains the rules and exact request params. Spring knows to handle this with findAll(Example<T> example) already. The result is a capable search in only 1 endpoint;

        Page<BookSimpleResponse> responsePage = bookPage.map(book -> BookMapper.toSimpleResponse(book)); // Note: mapping to simple response;
        return responsePage;
    }

    @Transactional
    public BookSimpleResponse updateBookById(Long id, UpdateBookRequest bookRequest) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The book is missing from the database")
        );
        bookToUpdate.setTitle(bookRequest.getTitle());
        bookToUpdate.setPublishedYear(bookRequest.getPublishedYear());
        bookToUpdate.setGenre(bookRequest.getGenre());

        //Book savedBook = bookRepository.save(bookToUpdate);    Note: @Transactional & .set will automaticly update the db, no need for this line anymore;
        return BookMapper.toSimpleResponse(bookToUpdate);
    }

    @Transactional
    // Note: For orphanRemoval = true to work we need @Transactional so that Hibernate follows for Dirty Checkings;
    public void deleteBookById(Long id) {
        Book bookToDelete = bookRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The book is missing from the database")
        );

        Author author = bookToDelete.getAuthor();
        Publisher publisher = bookToDelete.getPublisher();

        author.removeBook(bookToDelete);
        publisher.removeBook(bookToDelete);
    }


}
