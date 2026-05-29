package com.bookrental.app.dto.reviewdto;

import com.bookrental.app.dto.librarydto.LibrarySimpleResponse;
import com.bookrental.app.dto.userdto.UserSimpleResponse;
import com.bookrental.app.enums.ReviewRating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSimpleResponse {
    private Long id;
    private String description;
    private ReviewRating rating;
    private LibrarySimpleResponse library;
    private UserSimpleResponse user;
}
