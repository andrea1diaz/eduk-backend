package com.eduk.repository;

import com.eduk.model.Content;
import com.eduk.model.Rate;
import com.eduk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Boolean existsByContentAndUser(Content content, User user);

    long deleteByContent(Content content);

    Rate findByContentAndUser(Content content, User user);

    @Query(value = "SELECT AVG(rate) AS avgRating FROM rating WHERE user_id=:id", nativeQuery = true)
    Optional<Double> getAvgRating(@Param("id") Long id);
}
