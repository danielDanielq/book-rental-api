package com.bookrental.app.service;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.bookdto.CreateBookRequest;
import com.bookrental.app.dto.bookdto.UpdateBookRequest;
import com.bookrental.app.entity.Author;
import com.bookrental.app.entity.Book;
import com.bookrental.app.entity.Publisher;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.mapper.BookMapper;
import com.bookrental.app.repository.AuthorRepository;
import com.bookrental.app.repository.BookRepository;
import com.bookrental.app.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return BookMapper.toSimpleDTO(savedBook);
    }

    public BookSimpleResponse getBookById(Long id) {
        Book bookToFind = bookRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The book is missing from the database")// Note: Fail-Fast
        );

        return BookMapper.toSimpleDTO(bookToFind);
    }

    public Page<BookSimpleResponse> getAllBooks(int page, int size) { // Note: page number and the entity size;
        PageRequest pageRequest = PageRequest.of(page, size); // Note: this is the page request;
        Page<Book> bookPage = bookRepository.findAll(pageRequest); // Note: asking the db for this exact page which contains a list of the entities;

        Page<BookSimpleResponse> responsePage = bookPage.map(book -> BookMapper.toSimpleDTO(book)); // Note: mapping to simple response;
        return responsePage;
    }

    @Transactional
    public BookSimpleResponse updateBookById(Long id, UpdateBookRequest bookRequest) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(
                () ->  new ResouceNotFoundException("The book is missing from the database")
        );
        bookToUpdate.setTitle(bookRequest.getTitle());
        bookToUpdate.setPublishedYear(bookRequest.getPublishedYear());
        bookToUpdate.setGenre(bookRequest.getGenre());

        //Book savedBook = bookRepository.save(bookToUpdate);    Note: @Transactional & .set will automaticly update the db, no need for this line anymore;
        return BookMapper.toSimpleDTO(bookToUpdate);
    }

    @Transactional // Note: For orphanRemoval = true to work we need @Transactional so that Hibernate follows for Dirty Checkings;
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
