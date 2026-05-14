package com.bookrental.app.controller;

import com.bookrental.app.dto.wishlistdto.WishlistRequest;
import com.bookrental.app.dto.wishlistdto.WishlistSimpleResponse;
import com.bookrental.app.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<WishlistSimpleResponse> register(
            @PathVariable Long bookId,
            @Valid @RequestBody WishlistRequest wishlistRequest){
        WishlistSimpleResponse response = wishlistService.createWishlist(bookId, wishlistRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<WishlistSimpleResponse> getWishlist(
            @PathVariable Long bookId){
        WishlistSimpleResponse response = wishlistService.deleteBookFromWishlist(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<WishlistSimpleResponse>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        if (page > 100){
            page = 100;
        }
        if (page < 0) {
            page = 0;
        }
        if (size > 100){
            size = 100;
        }
        Page<WishlistSimpleResponse> responses = wishlistService.searchWishlists(userId, page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
