package com.bookrental.app.dto.publisherdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class PublisherSimpleResponse {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String city;
}
