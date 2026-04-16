package com.bookrental.app.dto.authordto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AuthorRequest {
    @NotBlank(message = "This field can't be empty")
    @Size(min = 2, max = 50, message = "First name size must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "This field can't be empty")
    @Size(min = 6, max = 50, message = "Last name size must be between 6 and 50 characters")
    private String lastName;

    @NotBlank(message = "This field can't be empty")
    private String country;

    @NotBlank(message = "This field can't be empty")
    private String city;
}
