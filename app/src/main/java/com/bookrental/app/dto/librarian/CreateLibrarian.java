package com.bookrental.app.dtos.librarian;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLibrarian {
    @Valid
    @NotNull
    SimpleLibrarian librarianData;
    @NotBlank(message = "password must be given")
    private String password;
}
