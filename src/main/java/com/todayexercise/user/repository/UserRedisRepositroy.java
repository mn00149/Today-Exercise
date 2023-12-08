package com.todayexercise.user.repository;

import com.todayexercise.user.dto.RedisUserDTO;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepositroy extends CrudRepository<RedisUserDTO, String> {
}