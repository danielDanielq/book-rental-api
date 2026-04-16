package com.bookrental.app.controller;

import com.bookrental.app.dto.publisherdto.PublisherRequest;
import com.bookrental.app.dto.publisherdto.PublisherSimpleResponse;
import com.bookrental.app.repository.PublisherRepository;
import com.bookrental.app.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherService publisherService;

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
    public ResponseEntity<Page<PublisherSimpleResponse>> getAll(@RequestParam int  page, @RequestParam int size) {
        Page<PublisherSimpleResponse> responses = publisherService.getAllPublishers(page, size);
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
