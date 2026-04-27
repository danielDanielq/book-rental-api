package com.bookrental.app.mapper;

import com.bookrental.app.dto.librarydto.LibraryRequest;
import com.bookrental.app.dto.librarydto.LibrarySimpleResponse;
import com.bookrental.app.entity.Library;

public class LibraryMapper {
    public static Library toEntity(LibraryRequest libraryRequest) {
        Library library = new Library();
        library.setName(libraryRequest.getName());
        library.setCity(libraryRequest.getCity());

        return library;
    }

    public static LibrarySimpleResponse toSimpleResponse(Library library) {
        LibrarySimpleResponse librarySimpleResponse = new LibrarySimpleResponse();
        librarySimpleResponse.setId(library.getId());
        librarySimpleResponse.setName(library.getName());
        librarySimpleResponse.setCity(library.getCity());

        return librarySimpleResponse;
    }
}
