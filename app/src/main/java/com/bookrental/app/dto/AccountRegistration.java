package com.bookrental.app.dtos;

import com.bookrental.app.enums.AccountRole;
import lombok.Data;


@Data
public class AccountRegistration {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private AccountRole role;
}
