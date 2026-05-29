package com.bookrental.app.mapper;

import com.bookrental.app.dto.authordto.AuthorBadResponse;
import com.bookrental.app.dto.authordto.AuthorRequest;
import com.bookrental.app.dto.authordto.AuthorSimpleResponse;
import com.bookrental.app.entity.Author;

public class AuthorMapper {
    public static AuthorSimpleResponse toSimpleResponse(Author author){
        AuthorSimpleResponse authorSimpleResponse = new AuthorSimpleResponse();
        authorSimpleResponse.setId(author.getId());
        authorSimpleResponse.setFirstName(author.getFirstName());
        authorSimpleResponse.setLastName(author.getLastName());
        authorSimpleResponse.setCountry(author.getCountry());
        authorSimpleResponse.setCity(author.getCity());

        return authorSimpleResponse;
    }

    public static Author toEntity(AuthorRequest authorRequest){
        Author author = new Author();
        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());
        author.setCountry(authorRequest.getCountry());
        author.setCity(authorRequest.getCity());

        return author;
    }

    public static AuthorBadResponse toBadResponse(Author author){
        AuthorBadResponse authorBadResponse = new AuthorBadResponse();
        authorBadResponse.setId(author.getId());
        authorBadResponse.setFirstName(author.getFirstName());
        authorBadResponse.setLastName(author.getLastName());
        authorBadResponse.setCountry(author.getCountry());
        authorBadResponse.setCity(author.getCity());
        authorBadResponse.setBooks(author.getBooks()); // Note: Here will be eager even if we have .lazy

        return authorBadResponse;
    }
}
