package com.bookrental.app.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserSimpleResponse { // Note: it is usefull if we want to get the whole users like GET /users; keep it simple so we don't get into N + 1
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
