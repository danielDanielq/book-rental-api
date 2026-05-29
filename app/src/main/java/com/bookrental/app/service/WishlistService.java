package com.bookrental.app.service;

import com.bookrental.app.dto.wishlistdto.WishlistRequest;
import com.bookrental.app.dto.wishlistdto.WishlistSimpleResponse;
import com.bookrental.app.entity.Book;
import com.bookrental.app.entity.User;
import com.bookrental.app.entity.Wishlist;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.mapper.WishlistMapper;
import com.bookrental.app.repository.BookRepository;
import com.bookrental.app.repository.UserRepository;
import com.bookrental.app.repository.WishlistRepository;
import com.bookrental.app.security.SecurityConfig;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public WishlistService(WishlistRepository wishlistRepository,  UserRepository userRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public WishlistSimpleResponse createWishlist(
            Long bookId,
            WishlistRequest wishlistRequest) {

        Long userId = SecurityConfig.getcurrentUserId();

        User userWishlist = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User with id " + userId + " not found")
        );

        Book bookToWishlist = bookRepository.findById(bookId).orElseThrow(
                () -> new ResouceNotFoundException("Book with id " + bookId + " not found")
        );

        Wishlist wishlist = WishlistMapper.toEntity(wishlistRequest);
        wishlist.setAddedDate(LocalDate.now());
        wishlist.setBook(bookToWishlist);
        wishlist.setUser(userWishlist);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return WishlistMapper.toSimpleResponse(savedWishlist);
    }

    @Transactional
    public WishlistSimpleResponse deleteBookFromWishlist(Long bookId) {

        Long userId = SecurityConfig.getcurrentUserId();

        User userWishlist = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User with id " + userId + " not found")
        );

        Book bookToDeleteFromWishlist = bookRepository.findById(bookId).orElseThrow(
                () -> new ResouceNotFoundException("Book with id " + bookId + " not found")
        );

        wishlistRepository.deleteByBookId(bookId);

        Wishlist wishlist = wishlistRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(
                () -> new ResouceNotFoundException("Wishlist with id " + userId + " not found")
        );

        return WishlistMapper.toSimpleResponse(wishlist);
    }

    public Page<WishlistSimpleResponse> searchWishlists(Long userId, int page, int size, String sort) {
        Wishlist probeWishlist = new Wishlist();
        User userWishlist = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User with id " + userId + " not found")
        );

        probeWishlist.setUser(userWishlist);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Wishlist> example = Example.of(probeWishlist, matcher);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Wishlist> wishlistPage = wishlistRepository.findAll(example, pageable);

        Page<WishlistSimpleResponse> responses = wishlistPage.map(WishlistMapper::toSimpleResponse);
        return responses;
    }


}
