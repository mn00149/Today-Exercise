package com.todayexercise.redis.controller;

import com.todayexercise.dto.CMRespDTO;
import com.todayexercise.redis.dto.RefreshTokenDTO;
import com.todayexercise.redis.model.RefreshToken;
import com.todayexercise.redis.repository.RefreshTokenRepository;
import com.todayexercise.redis.service.RefreshTokenService;
import com.todayexercise.user.dto.RedisUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @PostMapping("/redis/v1/get")
    public RefreshTokenDTO refreshTest(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        String id = refreshTokenDTO.getId();
        RefreshToken token = refreshTokenRepository.findById(id).get();
        RefreshTokenDTO result = new RefreshTokenDTO();
        result.setRefreshToken(token.getRefreshToken());
        result.setId(token.getId());
        return result;
    }// save

    @PostMapping("/redis/v1/update")
    public RefreshTokenDTO updateRefreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
        RefreshTokenDTO updatedRefreshTokenDTO = refreshTokenService.updateToken(refreshTokenDTO);
        return updatedRefreshTokenDTO;
    }
}
