package com.bookrental.app.entity;

import com.bookrental.app.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email",  length = 75, unique = true, nullable = false)
    private String  email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private Role role;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "address_id", nullable = false) // Note: We wil not accept a User without the Address set in;
    private Address address;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Rental> rentals = new ArrayList<>();

    public Address getAddress() {return address;}
    public void setAddress(Address address) {
        this.address = address;
        address.setUser(this);
    }

    public List<Rental> getRentals() {return rentals;}
    public void setRentals(List<Rental> rentals) {this.rentals = rentals;}

    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setUser(this);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
        rental.setUser(null);
    }
}
