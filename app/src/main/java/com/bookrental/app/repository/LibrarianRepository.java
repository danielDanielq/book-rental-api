package com.bookrental.app.repository;

import com.bookrental.app.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian,Long> {

    Optional<Librarian> findByEmail(String email); // Note: Spring query builder;
}
