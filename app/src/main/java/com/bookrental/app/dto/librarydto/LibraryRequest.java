package com.bookrental.app.dto.librarydto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LibraryRequest {
    @NotBlank(message = "This field can't be empty")
    @Size(min = 6, max = 50, message = "Name size must be between 6 and 50 characters")
    private String name;

    @NotBlank(message = "This field can't be empty")
    private String city;
}
