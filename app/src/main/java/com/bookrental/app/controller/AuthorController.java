package com.bookrental.app.controller;

import com.bookrental.app.dto.authordto.AuthorBadResponse;
import com.bookrental.app.dto.authordto.AuthorRequest;
import com.bookrental.app.dto.authordto.AuthorSimpleResponse;
import com.bookrental.app.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorSimpleResponse> register(@Valid @RequestBody AuthorRequest authorRequest) {
        AuthorSimpleResponse authorSimpleResponse = authorService.createAuthor(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorSimpleResponse);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorSimpleResponse> get(@PathVariable Long authorId) {
        AuthorSimpleResponse response = authorService.getAuthorById(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorSimpleResponse>> getAll(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        if (page > 100){
            page = 100;
        }
        if (page < 0) {
            page = 0;
        }
        if (size > 100){
            size = 100;
        }
        Page<AuthorSimpleResponse> responses = authorService.searchAuthors(firstName, lastName, country, city, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorSimpleResponse> update(@PathVariable Long authorId, @Valid @RequestBody AuthorRequest authorRequest) {
        AuthorSimpleResponse response = authorService.updateAuthorById(authorId, authorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> delete(@PathVariable Long authorId) {
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/bad/{authorId}")
    public ResponseEntity<AuthorBadResponse> getAuthorByIdBADex(@PathVariable Long authorId) {
        AuthorBadResponse response = authorService.getAuthorByIdBAD(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
