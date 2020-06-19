package com.eduk.repository;

import com.eduk.model.Content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, String> {

    Optional<Content> findById(String id);

}
