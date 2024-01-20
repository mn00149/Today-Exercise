package com.todayexercise.challengeCategory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todayexercise.category.Category;
import com.todayexercise.challenge.model.Challenge;
import com.todayexercise.user.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChallengeCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="challenge_category_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
}
