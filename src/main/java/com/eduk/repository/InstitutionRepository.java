package com.eduk.repository;

import com.eduk.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    @Query(value = "SELECT * FROM institutions WHERE id == id_  :n", nativeQuery = true)
    Optional<Institution> getById(@Param("n") Integer id_);
}
