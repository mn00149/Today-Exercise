package com.todayexercise.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todayexercise.config.auth.PrincipalDetails;
import com.todayexercise.dto.ResponseDTO;
import com.todayexercise.redis.repository.RefreshTokenRepository;
import com.todayexercise.excepton.customExceptionClass.UserNotFoundException;
import com.todayexercise.user.model.User;
import com.todayexercise.user.repository.UserRepository;
import com.todayexercise.util.CustomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        CustomUtil util = new CustomUtil();
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");
        //어세스토큰 만료시 리플래시 토큰 검증 요 방법 괜찬ㅎ으면 수정해서 코드 가독성 높히기 안괜찮으면 else문만 남기고 지울 것
        if(util.tokenIsExpired(token,JwtProperties.SECRET)) {
            ObjectMapper objectMapper = new ObjectMapper();

            response.setContentType("application/json");
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("access token 만료, refresh token 필요");
            //객체를 JSON으로 변경

            String result = objectMapper.writeValueAsString(responseDTO);

            response.getWriter().write(result);



        }else {
            System.out.println("header : " + header);
            System.out.println("실행2");
            // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
            // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
            // loadByUsername이 호출됨.
            String userId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("userId").asString();
            if (userId != null) {
                User user = userRepository.findByUserId(userId).orElseThrow(()
                        -> { return new UserNotFoundException("user id:"+userId);});


                // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
                // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                        null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                        principalDetails.getAuthorities());

                // 강제로 시큐리티의 세션에 접근하여 값 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);


                chain.doFilter(request, response);

            }
//            CustomUtil util = new CustomUtil();
//
//
//            //옵셔널 이므로 에러 처리 해줄것!!
//            // redis에 저장되어있는 토큰 정보를 만료된 access token으로 찾아온다.
//            RefreshToken foundTokenInfo = refreshTokenRepository.findByAccessToken(token).orElseThrow();
//            String refreshToken = foundTokenInfo.getRefreshToken();
//            // === Refresh Token 유효성 검사 === //
//            if(util.tokenIsExpired(refreshToken,JwtProperties.SECRET)) logger.error("refresh token expired");
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build();
//            DecodedJWT decodedJWT = verifier.verify(refreshToken);
//
//            // === Access Token 재발급 === //
//            String userId = foundTokenInfo.getId();
//
//            String accessToken = JWT.create()
//                    .withSubject(decodedJWT.getSubject())
//                    .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
//                    //.withClaim("id", )
//                    .withClaim("userId", userId)
//                    .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//
////            long now = System.currentTimeMillis();
////            String username = decodedJWT.getSubject();
////            Account account = accountRepository.findByUsername(username)
////                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
////            if (!account.getRefreshToken().equals(refreshToken)) {
////                throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
////            }
////            String accessToken = JWT.create()
////                    .withSubject(account.getUsername())
////                    .withExpiresAt(new Date(now + AT_EXP_TIME))
////                    .withClaim("roles", account.getRoles().stream().map(Role::getName)
////                            .collect(Collectors.toList()))
////                    .sign(Algorithm.HMAC256(JWT_SECRET));
////            Map<String, String> accessTokenResponseMap = new HashMap<>();
//
//


            }


    }

}