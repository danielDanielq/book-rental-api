package com.bookrental.app.dto.bookdto;

import com.bookrental.app.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class CreateBookRequest {
    @NotBlank(message = "This field can't be empty")
    private String title;

    @NotBlank(message = "This field can't be empty")
    @Size(min = 13, max = 13, message = "ISBN size must be 13 characters")
    private String isbn;

    @NotNull(message = "This field is required")
    private Integer publishedYear;

    @NotNull(message = "This field is required")
    private Integer totalCopies;

    private Genre genre;
}
