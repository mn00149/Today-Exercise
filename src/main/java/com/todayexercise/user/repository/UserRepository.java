package com.todayexercise.user.repository;

import com.todayexercise.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByUserId(String userId);

    User findByEmail(String email);
}
