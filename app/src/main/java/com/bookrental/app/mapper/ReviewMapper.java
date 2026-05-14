package com.bookrental.app.mapper;

import com.bookrental.app.dto.reviewdto.ReviewRequest;
import com.bookrental.app.dto.reviewdto.ReviewSimpleResponse;
import com.bookrental.app.entity.Review;

public class ReviewMapper {
    public static ReviewSimpleResponse toSimpleResponse(Review review){
        ReviewSimpleResponse reviewSimpleResponse = new ReviewSimpleResponse();
        reviewSimpleResponse.setId(review.getId());
        reviewSimpleResponse.setDescription(review.getDescription());
        reviewSimpleResponse.setRating(review.getRating());
        reviewSimpleResponse.setLibrary(LibraryMapper.toSimpleResponse(review.getLibrary()));

        return reviewSimpleResponse;
    }

    public static Review toEntity(ReviewRequest reviewRequest){
        Review review = new Review();
        review.setDescription(reviewRequest.getDescription());
        review.setRating(reviewRequest.getRating());

        return review;
    }
}
