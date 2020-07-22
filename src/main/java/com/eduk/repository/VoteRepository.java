package com.eduk.repository;

import com.eduk.model.Content;
import com.eduk.model.Vote;
import com.eduk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Boolean existsByContentAndUser(Content content, User user);

    Vote findByContentAndUser(Content content, User user);

    long deleteByContent(Content content);

    @Query(value = "SELECT COUNT(*) AS totalComments FROM likes WHERE user_id=:id", nativeQuery = true)
    Long getTotalVotes(@Param("id") Long id);
}
