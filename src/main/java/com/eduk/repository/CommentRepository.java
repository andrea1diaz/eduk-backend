package com.eduk.repository;

import com.eduk.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduk.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(long id);

    List<Comment> findAllByContent(Content content);

    @Query(value = "SELECT COUNT(*) AS totalComments FROM comments WHERE user_id=:id", nativeQuery = true)
    Long getTotalComments(@Param("id") Long id);
}
