package com.bookrental.app.entity;

import com.bookrental.app.enums.ReviewRating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", length = 150)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", nullable = false)
    private ReviewRating rating;

    @OneToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "library_id")
    private Library library;
}
