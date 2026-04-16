package com.bookrental.app.service;

import com.bookrental.app.dto.publisherdto.PublisherRequest;
import com.bookrental.app.dto.publisherdto.PublisherSimpleResponse;
import com.bookrental.app.entity.Publisher;
import com.bookrental.app.exception.AccountAlreadyExists;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.mapper.PublisherMapper;
import com.bookrental.app.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PublisherService {
    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public PublisherSimpleResponse createPublisher(PublisherRequest publisherRequest) {
        if (publisherRepository.findByEmail(publisherRequest.getEmail()).isPresent()) { // Note: Fail-Fast
            throw new AccountAlreadyExists("There is already a publisher with this name");
        }

        if (publisherRepository.findByName(publisherRequest.getName()).isPresent()) { // Note: Fail-Fast
            throw new AccountAlreadyExists("There is already a publisher with this name");
        }

        Publisher publisherToSave = PublisherMapper.toEntity(publisherRequest);
        Publisher savedPublisher = publisherRepository.save(publisherToSave);

        return PublisherMapper.toSimpleResponse(savedPublisher);
    }

    public PublisherSimpleResponse getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("There is no publisher with this id")
        );
        return PublisherMapper.toSimpleResponse(publisher);
    }

    public Page<PublisherSimpleResponse> getAllPublishers(int page, int size) { // Note: page number and the entity size;
        PageRequest pageRequest = PageRequest.of(page, size); // Note: this is the page request;
        Page<Publisher> publisherPage = publisherRepository.findAll(pageRequest); // Note: asking the db for this exact page which contains a list of the entities;

        Page<PublisherSimpleResponse> responsePage = publisherPage.map(p -> PublisherMapper.toSimpleResponse(p)); // Note: mapping to simple response;
        return  responsePage;
    }

    @Transactional
    public PublisherSimpleResponse updatePublisher(Long id, PublisherRequest publisherRequest) {
        Publisher publisherToUpdate = publisherRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The publisher is missing from the database")
        );
        publisherToUpdate.setName(publisherRequest.getName());
        publisherToUpdate.setEmail(publisherRequest.getEmail());
        publisherToUpdate.setCountry(publisherRequest.getCountry());
        publisherToUpdate.setCity(publisherRequest.getCity());

        //Publisher updatedPublisher = publisherRepository.save(publisherToUpdate);    Note: @Transactional & .set will automaticly update the db, no need for this line anymore;
        return PublisherMapper.toSimpleResponse(publisherToUpdate);
    }

    @Transactional // Note: Not needed because .delete() has implicitly implemented this;
    public PublisherSimpleResponse deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(
                () -> new ResouceNotFoundException("The publisher is missing from the database")
        );

        publisherRepository.delete(publisher);
        return PublisherMapper.toSimpleResponse(publisher);
    }

}
