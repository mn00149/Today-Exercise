package com.todayexercise.challenge.repository;

import com.todayexercise.challenge.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
@Query("select c from Challenge c join fetch c.userChallenges uc join fetch uc.user u where u.userId = :userId")
List<Challenge> findAllByUserId(@Param("userId") String userId);
}
