package com.bookrental.app.repository;

import com.bookrental.app.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> deleteByBookId(Long bookId);
    Optional<Wishlist> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("""
            SELECT w FROM Wishlist AS w
            JOIN FETCH w.user AS u JOIN FETCH w.book
            WHERE w.addedDate <= :date 
                AND u.id = :userId
            """)
    List<Wishlist> findWishlist7daysOldAndUserId(
            @Param("date") LocalDate date,
            @Param("userId") Long userId
    );

}
