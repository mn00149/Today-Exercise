package com.todayexercise.userChallenge.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todayexercise.challenge.model.Challenge;
import com.todayexercise.user.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_challenge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private String isOwner;

}
