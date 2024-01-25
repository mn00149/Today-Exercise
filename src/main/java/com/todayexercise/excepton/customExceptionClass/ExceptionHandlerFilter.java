package com.todayexercise.excepton.customExceptionClass;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todayexercise.config.jwt.JwtProperties;
import com.todayexercise.excepton.ErrorCode;
import com.todayexercise.util.CustomUtil;
import lombok.Data;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        CustomUtil util = new CustomUtil();
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        if(util.tokenIsExpired(token,JwtProperties.SECRET)) {
            setErrorResponse(response, 401);
            return;
        }
            filterChain.doFilter(request, response);

            //토큰의 유효기간 만료


    }
    private void setErrorResponse(
            HttpServletResponse response,
            int errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(401, "리플래시 토큰 필요");
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Data
    public static class ErrorResponse{
        private final Integer code;
        private final String message;
    }
}
