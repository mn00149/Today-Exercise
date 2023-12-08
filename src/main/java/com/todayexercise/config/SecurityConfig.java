package com.todayexercise.config;

import com.todayexercise.config.jwt.JwtAuthenticationFilter;
import com.todayexercise.config.jwt.JwtAuthorizationFilter;
import com.todayexercise.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity//해당 파일에서 시큐리티 설정
@Configuration
public class SecurityConfig {
    @Autowired
    CorsConfig corsConfig;

    @Autowired
    UserRepository userRepository;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
                .and()
                .formLogin().disable()//폼테그로 로그인 안되게 설정
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/login","/users/register", "/swagger-ui/**", "/swagger-resources/**","/v3/api-docs/**", "/redis/**").permitAll() //인증 필요 없는 url
                .anyRequest().authenticated()
                .and()
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }


}

