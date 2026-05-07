package com.bookrental.app.service;

import com.bookrental.app.dto.authordto.AuthorRequest;
import com.bookrental.app.dto.authordto.AuthorSimpleResponse;
import com.bookrental.app.entity.Author;
import com.bookrental.app.exception.AccountAlreadyExistsException;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.mapper.AuthorMapper;
import com.bookrental.app.repository.AuthorRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorSimpleResponse createAuthor(AuthorRequest authorRequest) {
        if (authorRepository.findByFirstNameAndLastName(authorRequest.getFirstName(), authorRequest.getLastName()).isPresent()) { // Note: Fail-Fast
            throw new AccountAlreadyExistsException("There is already an author with this name");
        }

        Author authorToSave = AuthorMapper.toEntity(authorRequest);
        Author savedAuthor = authorRepository.save(authorToSave);

        return AuthorMapper.toSimpleResponse(savedAuthor);
    }

    public AuthorSimpleResponse getAuthorById(Long authorId) {
        Author authorToFind = authorRepository.findById(authorId).orElseThrow(
                () -> new ResouceNotFoundException("The author is missing from the database")
        );

        return AuthorMapper.toSimpleResponse(authorToFind);
    }

    public Page<AuthorSimpleResponse> searchAuthors(String firstName, String lastName, String country, String city, int page, int size, String sort) { // Note: page number and the entity size;
        Author probeAuthor = new Author();

        probeAuthor.setFirstName(firstName);
        probeAuthor.setLastName(lastName);
        probeAuthor.setCountry(country);
        probeAuthor.setCity(city);

        ExampleMatcher matcher = ExampleMatcher.matching() // Note: Using this example matcher helps to search within the data containing the request params;
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> example = Example.of(probeAuthor, matcher);

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort)); // Note: this is the page request;
        Page<Author> authorPage = authorRepository.findAll(example ,pageRequest); // Note: asking the db for this exact page which contains a list of the entities;
                                                                                    // Note: searching by the example that contains the rules and exact request params. Spring knows to handle this with findAll(Example<T> example) already. The result is a capable search in only 1 endpoint;

        Page<AuthorSimpleResponse> responsePage = authorPage.map(p -> AuthorMapper.toSimpleResponse(p)); // Note: mapping to simple response;
        return responsePage;
    }

    @Transactional
    public AuthorSimpleResponse updateAuthorById(Long id, AuthorRequest authorRequest) { // Note: this request DTO is good to be used;
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The author is missing from the database")
        );
        authorToUpdate.setFirstName(authorRequest.getFirstName());
        authorToUpdate.setLastName(authorRequest.getLastName());
        authorToUpdate.setCountry(authorRequest.getCountry());
        authorToUpdate.setCity(authorRequest.getCity());

        //Author updatedAuthor = authorRepository.save(authorToUpdate);    Note: @Transactional & .set will automaticly update the db, no need for this line anymore;
        return AuthorMapper.toSimpleResponse(authorToUpdate);
    }

    @Transactional // Note: Not needed because .delete() has implicitly implemented this;
    public AuthorSimpleResponse deleteAuthorById(Long authorId) {
        Author authorToDelete = authorRepository.findById(authorId).orElseThrow(
                () -> new ResouceNotFoundException("The author is missing from the database")
        );

        authorRepository.delete(authorToDelete);
        return AuthorMapper.toSimpleResponse(authorToDelete);
    }

}
