package com.bookrental.app.dto.wishlistdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class WishlistRequest {
    @NotBlank(message = "This field can't be empty")
    private LocalDate addedDate;
}
