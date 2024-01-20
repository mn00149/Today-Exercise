package com.todayexercise.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.todayexercise.challengeCategory.model.ChallengeCategory;
import com.todayexercise.record.model.Record;
import com.todayexercise.userChallenge.model.UserChallenge;
import com.todayexercise.userTocategory.UserToCategory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Challenge {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="challenge_id")
    @Id
    private Long id;

    private String challengeName;
    private String address1;
    private String address2;
    private int personnel;

    @OneToMany(mappedBy = "challenge")
    @JsonIgnore
    private List<ChallengeCategory> challengeCategories;

    @OneToMany(mappedBy = "challenge")
    @JsonIgnore
    private List<UserChallenge> userChallenges;

    @OneToMany(mappedBy = "challenge")
    @JsonIgnore
    private List<Record> recordList = new ArrayList<>();

    public Challenge(Long id, int personnel) {
        this.id = id;
        this.personnel = personnel;
    }

    public void decrease(int personnel) {
        if (this.personnel - personnel < 0) {
            throw new RuntimeException("foo");
        }

        this.personnel -= personnel;
    }
}
