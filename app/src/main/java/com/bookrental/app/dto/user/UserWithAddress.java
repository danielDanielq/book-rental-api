package com.bookrental.app.dtos.user;

import com.bookrental.app.dtos.address.SimpleAddress;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserWithAddress {
    private Long id;
    @NotBlank(message = "email must be given")
    @Email
    private String email;
    @NotBlank(message = "first name must be given")
    private String firstName;
    @NotBlank(message = "last name must be given")
    private String lastName;
    @Valid
    @NotNull(message = "address must be given")
    private SimpleAddress address;
}
