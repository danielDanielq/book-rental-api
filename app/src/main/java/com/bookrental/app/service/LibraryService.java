package com.bookrental.app.service;

import com.bookrental.app.dto.librarydto.LibraryRequest;
import com.bookrental.app.dto.librarydto.LibrarySimpleResponse;
import com.bookrental.app.entity.Library;
import com.bookrental.app.exception.AccountAlreadyExistsException;
import com.bookrental.app.mapper.LibraryMapper;
import com.bookrental.app.repository.LibraryRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Transactional
    public LibrarySimpleResponse createLibrary(LibraryRequest libraryRequest) {
        if (libraryRepository.findByName(libraryRequest.getName()).isPresent()) {
            throw new AccountAlreadyExistsException("Library already exists");
        }

        Library toSaveLibrary = LibraryMapper.toEntity(libraryRequest);
        Library savedLibrary = libraryRepository.save(toSaveLibrary);

        return LibraryMapper.toSimpleResponse(savedLibrary);
    }

    public Page<LibrarySimpleResponse> searchLibraries(String name, String city, int page, int size, String sort) {
        Library probeLibrary = new Library();

        probeLibrary.setName(name);
        probeLibrary.setCity(city);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Library> example = Example.of(probeLibrary, matcher);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
        Page<Library> libraryPage = libraryRepository.findAll(example, pageRequest);

        Page<LibrarySimpleResponse> responsePage = libraryPage.map(LibraryMapper::toSimpleResponse);
        return responsePage;
    }

}
