package com.bookrental.app.dtos.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUser {
    @Valid
    @NotNull(message = "user data must be given")
    private UserWithAddress userData;
    @NotBlank(message = "password must be given")
    private String password;
}
