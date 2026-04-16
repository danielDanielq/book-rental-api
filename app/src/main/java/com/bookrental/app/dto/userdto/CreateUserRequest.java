package com.bookrental.app.dto.userdto;

import com.bookrental.app.dto.addressdto.AddressDto;
import com.bookrental.app.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "This field can't be empty")
    private String firstName;
    @NotBlank(message = "This field can't be empty")
    private String lastName;
    @NotBlank(message = "This field can't be empty")
    @Email(message = "E-mail format is not valid")
    private String email;
    @NotBlank(message = "This field can't be empty")
    @Size(min = 6, max = 50, message = "Password size must be between 6 and 50 characters")
    private String password;
    @Valid
    private AddressDto address;
    private Role role;
}
