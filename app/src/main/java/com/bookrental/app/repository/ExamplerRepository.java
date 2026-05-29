package com.bookrental.app.repository;

import com.bookrental.app.entity.Exampler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamplerRepository extends JpaRepository<Exampler, Long> {

    // Note: after we find the corresponding Exampler we have to reach the Rental tabel to be sure about the status and that the dates ar not overlapped;
    // Note: r.exampler = e is comparing the objects ids behind the scene;
    // Note: "NOT EXIST" = "chose this Exampler only and only if there are no Rentals that match"; similar with if( ! ... );
    // Note: We want to insert a rental that does not match other rentals that are PENDING, IN_PROGRESS or DELAYED and the dates are not overlapping so that we cand have multiple rentals with the same status but different dates;
    @Query("""
            SELECT e FROM Exampler AS e
            WHERE e.book.id = :bookId
                AND e.library.id = :libraryId
                AND NOT EXISTS (
                    SELECT r FROM Rental AS r 
                    WHERE r.exampler = e
                        AND r.rentalStatus IN ('PENDING', 'IN_PROGRESS', 'DELAYED')
                        AND r.startDate <= :endDate
                        AND r.endDate >= :startDate
                )
            """
    )
    List<Exampler> findAvailableExamplersForDates(
            @Param("bookId") Long bookId,
            @Param("libraryId") Long libraryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
