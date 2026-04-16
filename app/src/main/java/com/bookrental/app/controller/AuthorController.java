package com.bookrental.app.controller;

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
    private AuthorService authorService;

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
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort
    ) {
        Page<AuthorSimpleResponse> responses = authorService.getAllAuthors(firstName, lastName, country, city, page, size, sort);
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

}
