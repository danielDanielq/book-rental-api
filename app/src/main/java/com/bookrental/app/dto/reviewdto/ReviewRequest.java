package com.bookrental.app.dto.reviewdto;

import com.bookrental.app.enums.ReviewRating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReviewRequest {
    @NotBlank(message = "This field can't be empty")
    private String description;

    @NotBlank(message = "This field can't be empty")
    private ReviewRating rating;
}
