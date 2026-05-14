package com.bookrental.app.controller;

import com.bookrental.app.dto.rentaldto.RentalRequest;
import com.bookrental.app.dto.rentaldto.RentalSimpleResponse;
import com.bookrental.app.dto.validation.OrderedValidation;
import com.bookrental.app.enums.RentalStatus;
import com.bookrental.app.service.RentalService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // Note: @Validated works as well as a Fail-Fast;
    @PostMapping("/books/{bookId}/library/{libraryId}")
    public ResponseEntity<RentalSimpleResponse> insertRental(
            @PathVariable Long bookId,
            @PathVariable Long libraryId,
            @Validated(OrderedValidation.class) @RequestBody RentalRequest rentalRequest) {

        RentalSimpleResponse simpleResponse = rentalService.createRental(bookId, libraryId, rentalRequest.getStartDate(), rentalRequest.getEndDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(simpleResponse);
    }

    @PostMapping("/{rentalId}/return")
    public ResponseEntity<RentalSimpleResponse> returnRental(@PathVariable Long rentalId) {
        RentalSimpleResponse simpleResponse = rentalService.returnExampler(rentalId);
        return ResponseEntity.status(HttpStatus.OK).body(simpleResponse);
    }

    @GetMapping
    public ResponseEntity<Page<RentalSimpleResponse>> getAll(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) LocalDate returnDate,
            @RequestParam(required = false) RentalStatus rentalStatus,
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String libraryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ){
        if (page > 100){
            page = 100;
        }
        if (page < 0) {
            page = 0;
        }
        if (size > 100){
            size = 100;
        }

        Page<RentalSimpleResponse> responses = rentalService.searchRentals(startDate, endDate, returnDate, rentalStatus, userEmail, bookTitle, libraryName, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PatchMapping("/{rentalId}/status")
    @PreAuthorize("hasAuthority('ROLE_realm_librarian')")
    public ResponseEntity<RentalSimpleResponse> updateRental(
            @PathVariable Long rentalId,
            @RequestParam RentalStatus rentalStatus
            ) {
        RentalSimpleResponse response = rentalService.updateRentalStatus(rentalId, rentalStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
