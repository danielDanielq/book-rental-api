package com.bookrental.app.service;

import com.bookrental.app.entity.Book;
import com.bookrental.app.entity.Exampler;
import com.bookrental.app.entity.Publisher;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.repository.BookRepository;
import com.bookrental.app.repository.ExamplerRepository;
import com.bookrental.app.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamplerService {

    private ExamplerRepository examplerRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public ExamplerService(ExamplerRepository examplerRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.examplerRepository = examplerRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional // Note: All or Nothing;
    public void createExamplers(Long bookId, Long publisherId, int nrToCreate){
        Book bookToMultiply = bookRepository.findById(bookId).orElseThrow( // Note: Fail-Fast
                () -> new ResouceNotFoundException("Book with id: " + bookId + " not found")
        );

        Publisher publisherToMultiply = publisherRepository.findById(publisherId).orElseThrow( // Note: Fail-Fast
                () -> new ResouceNotFoundException("Publisher with id: " + publisherId + " not found")
        );

        List<Exampler> examplers = new ArrayList<>();
        for(int i = 0; i < nrToCreate; i++){
            Exampler exampler = new Exampler();
            exampler.setBook(bookToMultiply);
            exampler.setPublisher(publisherToMultiply);

            examplers.add(exampler);
        }

        examplerRepository.saveAll(examplers);
    }

    @Transactional
    public void createExamplersSlowBAD(Long bookId, Long publisherId, int nrToCreate) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResouceNotFoundException("Book with id: " + bookId + " not found")
        );
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow();

        for (int i = 0; i < nrToCreate; i++) {
            Exampler exampler = new Exampler();
            exampler.setBook(book);
            exampler.setPublisher(publisher);

            examplerRepository.saveAndFlush(exampler);
        }
    }
}
