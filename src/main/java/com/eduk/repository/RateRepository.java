package com.eduk.repository;

import com.eduk.model.Content;
import com.eduk.model.Rate;
import com.eduk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Boolean existsByContentAndUser(Content content, User user);

    Rate findByContentAndUser(Content content, User user);
}
