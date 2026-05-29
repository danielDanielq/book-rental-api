package com.bookrental.app.controller;

import com.bookrental.app.dto.reviewdto.ReviewRequest;
import com.bookrental.app.dto.reviewdto.ReviewSimpleResponse;
import com.bookrental.app.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    public final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/register/{libraryId}")
    public ResponseEntity<ReviewSimpleResponse> createReview(@RequestParam Long libraryId, @Valid @RequestBody ReviewRequest reviewRequest) {
        ReviewSimpleResponse response = reviewService.createReview(libraryId, reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
