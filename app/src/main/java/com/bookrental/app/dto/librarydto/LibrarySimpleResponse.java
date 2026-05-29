package com.bookrental.app.dto.librarydto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LibrarySimpleResponse {
    private Long id;
    private String name;
    private String city;
}
