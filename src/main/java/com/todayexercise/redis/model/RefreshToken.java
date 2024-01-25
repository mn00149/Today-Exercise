package com.todayexercise.redis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3)
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

//    @Indexed
//    private String accessToken;
}

//    redis에 저장할 객체를 정의한다.
//
//    @Indexed 어노테이션이 있어야, 해당 필드 값으로 데이터를 찾아올 수 있다.
//
//    만료된 access Token으로 refresh Token을 찾아와서 유효성을 검사할 생각이기 때문이다.
//
//    레디스 데이터의 유효시간은, timetoLive 옵션으로 refresh Token과 같은 시간인 3일로 지정하였다.