package com.bookrental.app.entity;

import com.bookrental.app.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;

    @Column(name = "due_date",nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;

    @ManyToOne( // Note: in 1:M, M:1 the Owner is the Many part and has the FKs;
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "book_id")
    private Book book;

    public Rental() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public LocalDateTime getRentalDate() {return rentalDate;}
    public void setRentalDate(LocalDateTime rentalDate) {this.rentalDate = rentalDate;}

    public LocalDateTime getDueDate() {return dueDate;}
    public void setDueDate(LocalDateTime dueDate) {this.dueDate = dueDate;}

    public LocalDateTime getReturnDate() {return returnDate;}
    public void setReturnDate(LocalDateTime returnDate) {this.returnDate = returnDate;}

    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;} // Note: no need to set the owner here;

    public Book getBook() {return book;}
    public void setBook(Book book) {this.book = book;}

}
