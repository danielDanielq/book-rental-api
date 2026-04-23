package com.bookrental.app.controller;

import com.bookrental.app.service.BookService;
import com.bookrental.app.service.ExamplerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examplers")
public class ExamplerController {
    private final ExamplerService examplerService;

    public ExamplerController(ExamplerService examplerService) {
        this.examplerService = examplerService;
    }

    @PostMapping("/{bookId}/{publisherId}/{nrToCreate}")
    public ResponseEntity<Void> createBatch(
            @PathVariable Long bookId,
            @PathVariable Long publisherId,
            @PathVariable int nrToCreate) {

        examplerService.createExamplers(bookId, publisherId, nrToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Note: BAD scenario;
    @PostMapping("/bad/{bookId}/{publisherId}/{nrToCreate}")
    public ResponseEntity<Void> createBADBatch(
            @PathVariable Long bookId,
            @PathVariable Long publisherId,
            @PathVariable int nrToCreate) {

        examplerService.createExamplersSlowBAD(bookId, publisherId, nrToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
