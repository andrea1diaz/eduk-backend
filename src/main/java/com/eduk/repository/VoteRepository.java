package com.eduk.repository;

import com.eduk.model.Content;
import com.eduk.model.Vote;
import com.eduk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Boolean existsByContentAndUser(Content content, User user);

    Vote findByContentAndUser(Content content, User user);
}
