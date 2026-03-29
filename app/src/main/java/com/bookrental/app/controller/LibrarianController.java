package com.bookrental.app.controllers;

import com.bookrental.app.dtos.librarian.CreateLibrarian;
import com.bookrental.app.dtos.librarian.SimpleLibrarian;
import com.bookrental.app.entities.Librarian;
import com.bookrental.app.mappers.LibrarianMapper;
import com.bookrental.app.mappers.UserMapper;
import com.bookrental.app.repositories.LibrarianRepository;
import com.bookrental.app.services.implementations.LibrarianService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/librarians")
class LibrarianController {

    private final LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @PostMapping
    public ResponseEntity<SimpleLibrarian> create(@Valid @RequestBody CreateLibrarian librarian) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LibrarianMapper.toSimpleLibrarian(librarianService.save(
                                        LibrarianMapper.toLibrarian(librarian), librarian.getPassword()
                                )
                        )
                );
    }

    @GetMapping("{librarianId}")
    public ResponseEntity<SimpleLibrarian> get(@PathVariable Long librarianId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(LibrarianMapper.toSimpleLibrarian(
                                librarianService.getById(librarianId)
                        )
                );
    }

}
