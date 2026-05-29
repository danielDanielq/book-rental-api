package com.bookrental.app.controller;

import com.bookrental.app.dto.publisherdto.PublisherRequest;
import com.bookrental.app.dto.publisherdto.PublisherSimpleResponse;
import com.bookrental.app.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public ResponseEntity<PublisherSimpleResponse> register(@Valid @RequestBody PublisherRequest publisherRequest) {
        PublisherSimpleResponse publisherSimpleResponse = publisherService.createPublisher(publisherRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherSimpleResponse);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<PublisherSimpleResponse> get(@PathVariable Long publisherId) {
        PublisherSimpleResponse response = publisherService.getPublisherById(publisherId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PublisherSimpleResponse>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int  page,
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
        Page<PublisherSimpleResponse> responses = publisherService.searchPublishers(name, email, country, city, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("{publisherId}")
    public ResponseEntity<PublisherSimpleResponse> update(@PathVariable Long publisherId, @Valid @RequestBody PublisherRequest publisherRequest) {
        PublisherSimpleResponse publisherSimpleResponse = publisherService.updatePublisher(publisherId,publisherRequest);
        return ResponseEntity.status(HttpStatus.OK).body(publisherSimpleResponse);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> delete(@PathVariable Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
