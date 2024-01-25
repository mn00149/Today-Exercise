package com.todayexercise.user.repository;

import com.todayexercise.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Optional<User> findByUserId(String userId);

    Optional<User> findById(Long id);

    User findByEmail(String email);
}
