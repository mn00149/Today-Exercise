package com.todayexercise.challengeCategory.repository;

import com.todayexercise.challengeCategory.model.ChallengeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeCategoryRepository extends JpaRepository<ChallengeCategory, Integer> {
}
