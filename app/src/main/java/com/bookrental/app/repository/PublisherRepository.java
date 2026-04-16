package com.bookrental.app.repository;

import com.bookrental.app.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name); // Note: Spring query builder;
    Optional<Publisher> findByEmail(String email);
}
