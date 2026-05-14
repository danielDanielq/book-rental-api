package com.bookrental.app.entity;

import com.bookrental.app.enums.BookGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter @Setter @NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "isbn", length = 13, nullable = false, unique = true) // Note: The official length of an International Standard Book Number (ISBN) has been 13 digits since January 1, 2007
    private String isbn;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false, length = 50)
    private BookGenre genre;

    @Column(name = "rental_contor", nullable = false)
    private Integer rentalContor = 0;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @OneToMany( // Note: 1 book = more wishlist for different users;
            mappedBy = "book",
            cascade = CascadeType.PERSIST, // Note: if
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Wishlist> wishlists = new ArrayList<>();
}
