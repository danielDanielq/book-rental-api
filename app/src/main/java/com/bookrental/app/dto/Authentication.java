package com.bookrental.app.dtos;

import lombok.Data;

@Data
public class Authentication {
    private String email;
    private String password;
}
