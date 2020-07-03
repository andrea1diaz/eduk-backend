package com.eduk.repository;

import com.eduk.model.Content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    Optional<Content> findById(long id);

    @Query(value = "SELECT * FROM contents ORDER BY score ASC", nativeQuery = true)
	Optional<List<Content>> getContentsAll();

    @Query(value = "SELECT * FROM contents NATURAL JOIN (SELECT content_id AS id FROM keywords K WHERE K.keywords IN :selected_keywords) AS K ORDER BY score ASC", nativeQuery = true)
	Optional<List<Content>> getContentByKeywords(@Param("selected_keywords") List<String> keywords);

    @Query(value = "SELECT * FROM contents ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
	Optional<List<Content>> getContentsRandom();

}
