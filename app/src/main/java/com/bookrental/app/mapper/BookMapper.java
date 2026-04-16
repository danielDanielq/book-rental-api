package com.bookrental.app.mapper;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.bookdto.CreateBookRequest;
import com.bookrental.app.entity.Book;

public class BookMapper {
    public static BookSimpleResponse toSimpleDTO(Book book) {
        BookSimpleResponse bookSimpleResponse = new BookSimpleResponse();
        bookSimpleResponse.setId(book.getId());
        bookSimpleResponse.setTitle(book.getTitle());
        bookSimpleResponse.setPublisher(PublisherMapper.toSimpleResponse(book.getPublisher()));
        bookSimpleResponse.setAuthor(AuthorMapper.toSimpleResponse(book.getAuthor()));
        bookSimpleResponse.setIsbn(book.getIsbn());
        bookSimpleResponse.setPublishedYear(book.getPublishedYear());
        bookSimpleResponse.setGenre(book.getGenre());

        return bookSimpleResponse;
    }

    public static Book toEntity(CreateBookRequest createBookRequest) {
        Book book = new Book();
        book.setTitle(createBookRequest.getTitle());
        book.setIsbn(createBookRequest.getIsbn());
        book.setPublishedYear(createBookRequest.getPublishedYear());
        book.setTotalCopies(createBookRequest.getTotalCopies());
        book.setAvailableCopies(createBookRequest.getTotalCopies()); // Note: this value is the same when added;
        book.setGenre(createBookRequest.getGenre());

        return book;
    }

}
