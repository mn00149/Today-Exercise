package com.todayexercise.config.jwt;

public interface JwtProperties {
    String SECRET = "dhdlsdnd"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 1000*60*5;   //864000000; // 10일 (1/1000초)

    int REFRESH_EXPIRATION_TIME = 1000*400;   //864000000; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String REFRESH_HEADER_STRING = "Refresh_Authorization";
}
