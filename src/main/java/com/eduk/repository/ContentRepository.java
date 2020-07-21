package com.eduk.repository;

import com.eduk.model.Content;

import com.eduk.model.Subject;
import com.eduk.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    Optional<Content> findById(long id);

    List<Content> findAllByUser(User user);

    List<Content> findAllBySubject(Subject subject);

    @Query(value = "SELECT * FROM contents ORDER BY rating DESC, score DESC LIMIT 4", nativeQuery = true)
	Optional<List<Content>> getContentsAll();

    @Query(value = "SELECT * " +
            "FROM contents c " +
            "JOIN keywords k ON k.content_id = c.id " +
            "JOIN subjects s ON s.id = c.subject_id " +
            "WHERE k.keywords IN :selected_keywords " +
            "OR s.name IN :selected_keywords " +
            "ORDER BY rating DESC"
            , nativeQuery = true)
	Optional<List<Content>> getContentByKeywords(@Param("selected_keywords") List<String> keywords);

	@Query(value = "SELECT * FROM contents WHERE subject_id = :selected_subject ORDER BY rating DESC, score DESC LIMIT 4", nativeQuery = true)
	Optional<List<Content>> getBySubject(@Param("selected_subject") Long subjectId);

    @Query(value = "SELECT * FROM contents ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
	Optional<List<Content>> getContentsRandom();

    @Query(value = "SELECT COUNT(*) AS totalContents FROM contents WHERE user_id=:id", nativeQuery = true)
    Long getTotalContents(@Param("id") Long id);

}
