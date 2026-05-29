package com.bookrental.app.dto.publisherdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PublisherRequest {
    @NotBlank(message = "This field can't be empty")
    @Size(min = 6, max = 50, message = "Name size must be between 6 and 50 characters")
    private String name;

    @NotBlank(message = "This field can't be empty")
    @Email(message = "E-mail format is not valid")
    private String email;

    @NotBlank(message = "This field can't be empty")
    private String country;

    @NotBlank(message = "This field can't be empty")
    private String city;
}
