package com.todayexercise.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todayexercise.config.jwt.JwtProperties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;

public class CustomUtil {
    public String getUserIdByJWT(HttpServletRequest request){
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        String userId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("userId").asString();
        return userId;
    }

    public boolean tokenIsExpired(String token, String secretKey) {
//        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiredDate = decodedJWT.getExpiresAt();
        return expiredDate.before(new Date());
    }

    public String createAccessToken(String userId){
        String accessToken = JWT.create()
                .withSubject("access token")
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                //.withClaim("id", principalDetailis.getUser().getId())
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return accessToken;
    }

    public String createRefreshToken(String userId){
        String refreshToken = JWT.create()
//                .withSubject(principalDetailis.getUsername())
                .withSubject("refresh token")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return refreshToken;
    }
}
