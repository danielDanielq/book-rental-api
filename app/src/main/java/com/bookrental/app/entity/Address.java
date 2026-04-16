package com.bookrental.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter @Setter @NoArgsConstructor
public class Address { // Note: This entity will be related only to the User, the others (Author & Publisher) wil be a KISS (keep it simple);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", length = 75)
    private String country;

    @Column(name = "city", length = 75)
    private String city;

    @Column(name = "street", length = 75)
    private String street;

    @Column(name = "building_number", length = 75)
    private String buildingNumber;

    @OneToOne( // Note: no cascade utility needed here;
            mappedBy = "address",
            fetch = FetchType.LAZY
    )
    private User user;

}