package com.todayexercise.category;

import com.todayexercise.userTocategory.UserToCategory;
import lombok.Data;

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
    private List<UserToCategory> userToCategories;
}
