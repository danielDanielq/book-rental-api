package com.bookrental.app.mapper;

import com.bookrental.app.dto.publisherdto.PublisherRequest;
import com.bookrental.app.dto.publisherdto.PublisherSimpleResponse;
import com.bookrental.app.entity.Publisher;

public class PublisherMapper {

    public static PublisherSimpleResponse toSimpleResponse(Publisher publisher) {
        PublisherSimpleResponse publisherSimpleResponse = new PublisherSimpleResponse();
        publisherSimpleResponse.setId(publisher.getId());
        publisherSimpleResponse.setName(publisher.getName());
        publisherSimpleResponse.setEmail(publisher.getEmail());
        publisherSimpleResponse.setCountry(publisher.getCountry());
        publisherSimpleResponse.setCity(publisher.getCity());

        return publisherSimpleResponse;
    }

    public static Publisher toEntity(PublisherRequest publisherRequest) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherRequest.getName());
        publisher.setEmail(publisherRequest.getEmail());
        publisher.setCountry(publisherRequest.getCountry());
        publisher.setCity(publisherRequest.getCity());

        return publisher;
    }
}
