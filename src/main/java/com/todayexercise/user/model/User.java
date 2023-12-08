package com.todayexercise.user.model;


import com.todayexercise.userTocategory.UserToCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_index")
    private Long id;
    private String userId;
    private String username;
    private String password;
    private String address1;
    private String address2;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<UserToCategory> userToCategories;

}
