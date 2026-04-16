package com.bookrental.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
@Getter @Setter @NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "email", length = 75, unique = true, nullable = false)
    private String email;

    @Column(name = "country", length = 75)
    private String country;

    @Column(name = "city", length = 75)
    private String city;

    @OneToMany(
            mappedBy = "publisher",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book){
        books.remove(book);
        book.setPublisher(null);
    }
}
