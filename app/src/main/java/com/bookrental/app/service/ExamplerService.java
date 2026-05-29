package com.bookrental.app.service;

import com.bookrental.app.entity.Book;
import com.bookrental.app.entity.Exampler;
import com.bookrental.app.entity.Library;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.repository.BookRepository;
import com.bookrental.app.repository.ExamplerRepository;
import com.bookrental.app.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamplerService {

    private ExamplerRepository examplerRepository;
    private BookRepository bookRepository;
    private LibraryRepository libraryRepository;

    public ExamplerService(ExamplerRepository examplerRepository, BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.examplerRepository = examplerRepository;
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Transactional // Note: All or Nothing;
    public void createExamplers(Long bookId, Long libraryId, int nrToCreate){
        Book bookToMultiply = bookRepository.findById(bookId).orElseThrow( // Note: Fail-Fast
                () -> new ResouceNotFoundException("Book with id: " + bookId + " not found")
        );

        Library libraryToMultiply = libraryRepository.findById(libraryId).orElseThrow( // Note: Fail-Fast
                () -> new ResouceNotFoundException("Library with id: " + libraryId + " not found")
        );

        List<Exampler> examplers = new ArrayList<>();
        for(int i = 0; i < nrToCreate; i++){
            Exampler exampler = new Exampler();
            exampler.setBook(bookToMultiply);
            exampler.setLibrary(libraryToMultiply);

            examplers.add(exampler);
        }

        examplerRepository.saveAll(examplers);
    }

    @Transactional
    public void createExamplersSlowBAD(Long bookId, Long libraryId, int nrToCreate) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResouceNotFoundException("Book with id: " + bookId + " not found")
        );
        Library library = libraryRepository.findById(libraryId).orElseThrow( // Note: Fail-Fast
                () -> new ResouceNotFoundException("Library with id: " + libraryId + " not found")
        );

        for (int i = 0; i < nrToCreate; i++) {
            Exampler exampler = new Exampler();
            exampler.setBook(book);
            exampler.setLibrary(library);

            examplerRepository.saveAndFlush(exampler);
        }
    }
}
