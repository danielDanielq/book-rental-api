package com.bookrental.app.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserDetailsResponse { // Note: we want this response for specifically 1 user not on all N users so we don't attract N + 1 querry. Ex: GET /users/5
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
