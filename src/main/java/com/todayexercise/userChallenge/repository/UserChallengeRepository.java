package com.todayexercise.userChallenge.repository;

import com.todayexercise.userChallenge.model.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Integer> {
}
