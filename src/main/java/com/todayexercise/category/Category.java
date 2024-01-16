package com.todayexercise.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.todayexercise.challengeCategory.model.ChallengeCategory;
import com.todayexercise.userTocategory.UserToCategory;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;
    private String category;

//    private LocalDateTime createDate;


    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    @JsonIgnore
    private List<UserToCategory> userToCategories;


    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<ChallengeCategory> challengeCategories;
}
