package com.bookrental.app.controller;

import com.bookrental.app.dto.librarydto.LibraryRequest;
import com.bookrental.app.dto.librarydto.LibrarySimpleResponse;
import com.bookrental.app.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/libraries")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public ResponseEntity<LibrarySimpleResponse> register (@Valid @RequestBody LibraryRequest libraryRequest) {
        LibrarySimpleResponse response = libraryService.createLibrary(libraryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<LibrarySimpleResponse>> getAll(
            @RequestParam(required = false) String name,
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

        Page<LibrarySimpleResponse> responses = libraryService.searchLibraries(name, city, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

}
