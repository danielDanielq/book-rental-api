package com.bookrental.app.dto.authordto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class AuthorSimpleResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
}
