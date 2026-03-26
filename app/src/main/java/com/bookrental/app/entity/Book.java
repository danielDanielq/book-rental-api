package com.bookrental.app.entity;

import com.bookrental.app.enums.Genre;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "author", length = 125, nullable = false)
    private String author;

    @Column(name = "isbn", length = 13, nullable = false, unique = true) // Note: The official length of an International Standard Book Number (ISBN) has been 13 digits since January 1, 2007
    private String isbn;

    @Column(name = "published_year", length = 50)
    private Integer publishedYear;

    @Column(name = "total_copies", length = 50, nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", length = 50, nullable = false)
    private Integer availableCopies;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false, length = 50)
    private Genre genre;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Rental> rentals;

    public Book() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public Integer getPublishedYear() {return publishedYear;}
    public void setPublishedYear(Integer publishedYear) {this.publishedYear = publishedYear;}

    public Integer getTotalCopies() {return totalCopies;}
    public void setTotalCopies(Integer totalCopies) {this.totalCopies = totalCopies;}

    public Integer getAvailableCopies() {return availableCopies;}
    public void setAvailableCopies(Integer availableCopies) {this.availableCopies = availableCopies;}

    public Genre getGenre() {return genre;}
    public void setGenre(Genre genre) {this.genre = genre;}

    public List<Rental> getRentals() {return rentals;}
    public void setRentals(List<Rental> rentals) {this.rentals = rentals;}

    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setBook(this);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
        rental.setBook(null);
    }
}
