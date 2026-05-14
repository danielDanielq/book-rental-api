package com.bookrental.app.repository;

import com.bookrental.app.entity.Rental;
import com.bookrental.app.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // Note: !! If you use multiple JOINs you can get into a big cartesian multiplier and 10 pieces page will fail !!
    @Query("""
            SELECT r FROM Rental AS r
            JOIN FETCH r.exampler AS e JOIN FETCH e.book JOIN FETCH e.library JOIN FETCH r.user
            WHERE r.startDate <= :date 
                AND r.rentalStatus = :status 
            """
    )
    List<Rental> findPendingByStartDateBefore(
            @Param("date") LocalDate date,
            @Param("status") RentalStatus status);


    @Query("""
            SELECT r FROM Rental AS r
            JOIN FETCH r.exampler AS e JOIN FETCH e.book JOIN FETCH e.library JOIN FETCH r.user
            WHERE r.endDate <= :date 
                AND r.rentalStatus = :status
            """)
    List<Rental> findInProgressByEndDateBefore(
            @Param("date") LocalDate date,
            @Param("status") RentalStatus status);


    // Note: This query is still dangerous because it does not apply Pagination;
    @Query(""" 
            SELECT r FROM Rental AS r
            JOIN FETCH r.exampler AS e JOIN FETCH e.book JOIN FETCH e.library JOIN FETCH r.user
            WHERE r.startDate = :date 
                AND r.rentalStatus = :status
            """
    )
    List<Rental> findPendingByExactStartDate(
            @Param("date") LocalDate date,
            @Param("status") RentalStatus status);


    boolean existsByExamplerIdAndRentalStatus(Long examplerId, RentalStatus rentalStatus);
}
