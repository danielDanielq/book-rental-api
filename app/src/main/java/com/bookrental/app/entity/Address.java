package com.bookrental.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
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

    public Address() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getStreet() {return street;}
    public void setStreet(String street) {this.street = street;}

    public String getBuildingNumber() {return buildingNumber;}
    public void setBuildingNumber(String buildingNumber) {this.buildingNumber = buildingNumber;}

    public User getUser() {return user;}
    public void setUser(User user) {
        this.user = user;
        user.setAddress(this);
    }
}
