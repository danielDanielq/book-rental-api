package com.bookrental.app.controller;

import com.bookrental.app.dto.rentaldto.RentalRequest;
import com.bookrental.app.dto.rentaldto.RentalSimpleResponse;
import com.bookrental.app.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/books/{bookId}/library/{libraryId}")
    public ResponseEntity<RentalSimpleResponse> insertRental(
            @PathVariable Long bookId,
            @PathVariable Long libraryId,
            @RequestBody RentalRequest rentalRequest) {

        RentalSimpleResponse simpleResponse = rentalService.createRental(bookId, libraryId, rentalRequest.getStartDate(), rentalRequest.getEndDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(simpleResponse);
    }

    @PostMapping("/{rentalId}/return")
    public ResponseEntity<RentalSimpleResponse> returnRental(@PathVariable Long rentalId) {
        RentalSimpleResponse simpleResponse = rentalService.returnExampler(rentalId);
        return ResponseEntity.status(HttpStatus.OK).body(simpleResponse);
    }

}
