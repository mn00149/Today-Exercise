package com.todayexercise.redis.repository;

import com.todayexercise.redis.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    //Optional<RefreshToken> findByAccessToken(String accessToken);

}
