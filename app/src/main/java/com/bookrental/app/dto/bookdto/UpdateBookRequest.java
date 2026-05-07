package com.bookrental.app.dto.bookdto;

import com.bookrental.app.enums.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UpdateBookRequest { // Note: This DTO will not let the update endpoint change the ISBN, total copies or available copies;
    @NotBlank(message = "This field can't be empty")
    private String title;

    @NotNull(message = "This field is required")
    private Integer publishedYear;

    @NotNull(message = "Genre is required")
    private BookGenre genre;
}
