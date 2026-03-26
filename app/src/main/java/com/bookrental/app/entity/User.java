package com.bookrental.app.entity;

import com.bookrental.app.enums.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User { // Note: User = Client and Librarian
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

    @Column(name = "password_hash", length = 255, nullable = false) // Note: Standard length for hashed passwords;
    private String passwordHash;

    @Enumerated(EnumType.STRING) // Note: Hibernate and Enums --Dehydration (by index)--> Integer; Explicitly changed (by name) so it prevents issues if enum order changes;
    @Column(name = "role", nullable = false, length = 50)
    private Role role;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Rental> rentals = new ArrayList<>();

    public User() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPasswordHash() {return passwordHash;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}

    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}

    public Address getAddress() {return address;}
    public void setAddress(Address address) {this.address = address;} // Note: no need to set the user here for the Address, it will enter a infinite loop;

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
