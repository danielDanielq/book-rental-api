package com.bookrental.app.services.implementations;

import com.bookrental.app.dtos.AccountRegistration;
import com.bookrental.app.entities.Librarian;
import com.bookrental.app.mappers.LibrarianMapper;
import com.bookrental.app.repositories.LibrarianRepository;
import com.bookrental.app.services.interfaces.IdentityProviderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService {
    private final LibrarianRepository librarianRepository;
    private final IdentityProviderService identityProviderService;

    public LibrarianService(LibrarianRepository librarianRepository, IdentityProviderService identityProviderService) {
        this.librarianRepository = librarianRepository;
        this.identityProviderService = identityProviderService;
    }

    @Transactional
    public Librarian save(Librarian librarian,String password){
        Librarian savedLibrarian = librarianRepository.save(librarian);
        AccountRegistration accountRegistration = LibrarianMapper.toAccountRegistration(savedLibrarian,password);
        identityProviderService.create(accountRegistration);
        return savedLibrarian;
    }

    public Librarian getById(Long librarianId){
        return librarianRepository.findById(librarianId).orElseThrow(() -> new EntityNotFoundException("librarian not found"));
    }
}
