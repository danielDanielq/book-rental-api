package com.bookrental.app.dto.bookdto;

import com.bookrental.app.dto.authordto.AuthorSimpleResponse;
import com.bookrental.app.dto.publisherdto.PublisherSimpleResponse;
import com.bookrental.app.enums.BookGenre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BookSimpleResponse {
    private Long id;
    private String title;
    private AuthorSimpleResponse author;
    private PublisherSimpleResponse publisher;
    private String isbn;
    private Integer publishedYear;
    private BookGenre genre;
}
