package com.todayexercise.redis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
@Data
public class RefreshTokenDTO {

    private String id;

    private String refreshToken;

    private String accessToken;

    private String status;
}
