package com.todayexercise.user.controller;

import com.todayexercise.redis.service.RefreshTokenService;
import com.todayexercise.user.dto.RedisUserDTO;
import com.todayexercise.user.service.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRedisController {
    @Autowired
    UserRedisService service;
    @Autowired
    RefreshTokenService refreshTokenService;
    @PostMapping("/redis/v1/post")
    public RedisUserDTO addUser(@RequestBody RedisUserDTO user) {
        RedisUserDTO result = service.addUser(user);
        return result;
    }// save

}
