package com.bookrental.app.dto.authordto;

import com.bookrental.app.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AuthorBadResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private List<Book> books = new ArrayList<>();
}
