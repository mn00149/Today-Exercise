package com.todayexercise.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.todayexercise.record.model.Record;
import com.todayexercise.reply.model.Reply;
import com.todayexercise.userChallenge.model.UserChallenge;
import com.todayexercise.userTocategory.UserToCategory;
import io.lettuce.core.AclSetuserArgs;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    @Column(name="nickname")
    private String userId;
    private String username;
    private String password;
    private String address1;
    private String address2;
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserToCategory> userToCategories;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserChallenge> userChallenges;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Record> recordList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reply> replyList = new ArrayList<>();

}
