package com.bookrental.app.service;

import com.bookrental.app.dto.reviewdto.ReviewRequest;
import com.bookrental.app.dto.reviewdto.ReviewSimpleResponse;
import com.bookrental.app.entity.Library;
import com.bookrental.app.entity.Review;
import com.bookrental.app.entity.User;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.mapper.ReviewMapper;
import com.bookrental.app.repository.LibraryRepository;
import com.bookrental.app.repository.ReviewRepository;
import com.bookrental.app.repository.UserRepository;
import com.bookrental.app.security.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    public ReviewService(ReviewRepository reviewRepository, LibraryRepository libraryRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.libraryRepository = libraryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReviewSimpleResponse createReview(Long libraryId, ReviewRequest reviewRequest) {
        Long userId = SecurityConfig.getcurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User not found with id: " + userId)
        );

        Library library = libraryRepository.findById(libraryId).orElseThrow(
                () -> new ResouceNotFoundException("Library not found with id: " + libraryId)
        );


        Review savedReview = reviewRepository.save(ReviewMapper.toEntity(reviewRequest));
        return ReviewMapper.toSimpleResponse(savedReview);
    }

}
