package com.bookrental.app.dtos.librarian;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SimpleLibrarian {
    private Long id;
    @NotBlank(message = "email must be given")
    private String email;
    @NotBlank(message = "first name must be given")
    private String firstName;
    @NotBlank(message = "last name must be given")
    private String lastName;
}
