package com.bookrental.app.controller;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.bookdto.CreateBookRequest;
import com.bookrental.app.dto.bookdto.UpdateBookRequest;
import com.bookrental.app.enums.Genre;
import com.bookrental.app.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{authorId}/{publisherId}")
    public ResponseEntity<BookSimpleResponse> register(@PathVariable Long authorId,
                                                       @PathVariable Long publisherId,
                                                       @Valid @RequestBody CreateBookRequest createBookRequest) {
        BookSimpleResponse response = bookService.createBook(authorId, publisherId, createBookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookSimpleResponse> get(@PathVariable Long bookId) {
        BookSimpleResponse response = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<BookSimpleResponse>> getAll( // Note: this endpoint is a general searchig using Example, ExampleMatcher and pagination;
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer publishedYear,
            @RequestParam(required = false) Genre genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Page<BookSimpleResponse> response = bookService.searchBooks(title, isbn, publishedYear, genre, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookSimpleResponse> update(@PathVariable Long bookId ,@Valid @RequestBody UpdateBookRequest updateBookRequest) {
        BookSimpleResponse simpleResponse = bookService.updateBookById(bookId, updateBookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(simpleResponse);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookId) {
        bookService.deleteBookById(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
