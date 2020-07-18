package com.eduk.repository;

import com.eduk.model.Content;
import com.eduk.model.User;
import com.eduk.model.View;
import com.eduk.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {

    Boolean existsByContentAndUser(Content content, User user);

    @Query(value = "SELECT s.name, s.title, v.cont " +
            "FROM subjects s, (SELECT subject_id, COUNT(*) AS cont FROM views WHERE user_id=:id " +
            "GROUP BY subject_id) v " +
            "WHERE s.id = v.subject_id", nativeQuery = true)
    Optional<List<?>> getSubjectViews(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) AS totalViews FROM views WHERE user_id=:id", nativeQuery = true)
    Long getTotalViews(@Param("id") Long id);

    @Query(value = "SELECT s.title " +
            "FROM subjects s, " +
            "(SELECT subject_id, COUNT(*) AS cont " +
            "FROM views WHERE user_id=:id " +
            "GROUP BY subject_id) v " +
            "WHERE s.id = v.subject_id " +
            "ORDER BY v.cont " +
            "DESC LIMIT 1", nativeQuery = true)
    String getFavSubject(@Param("id") Long id);
}
