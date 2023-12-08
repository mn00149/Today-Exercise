package com.todayexercise.userTocategory;

import com.todayexercise.category.Category;
import com.todayexercise.user.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_category")
public class UserToCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_index")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
