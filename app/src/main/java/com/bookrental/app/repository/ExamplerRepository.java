package com.bookrental.app.repository;

import com.bookrental.app.entity.Exampler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamplerRepository extends JpaRepository<Exampler, Long> {
}
