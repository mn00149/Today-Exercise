package com.todayexercise.redis.service;

import com.todayexercise.excepton.customExceptionClass.RefresTokenException;
import com.todayexercise.redis.dto.RefreshTokenDTO;
import com.todayexercise.redis.model.RefreshToken;
import com.todayexercise.redis.repository.RefreshTokenRepository;
import com.todayexercise.util.CustomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveTokenInfo(String userId, String refreshToken, String accessToken) {
        //refreshTokenRepository.save(new RefreshToken(userId, refreshToken, accessToken));
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
    }



//    @Transactional
//    public void removeRefreshToken(String accessToken) {
//        refreshTokenRepository.findByAccessToken(accessToken)
//                .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
//    }

    @Transactional
    public RefreshTokenDTO updateToken(RefreshTokenDTO refreshTokenDTO) {
        //업대이트 토큰 디티오 새로 만들고 예외처리도 새로 수정 할 것!!
        System.out.println("실행1");
        CustomUtil util = new CustomUtil();
        String userId = refreshTokenDTO.getId();
        String sendedRefreshToken = refreshTokenDTO.getRefreshToken();

        RefreshToken refreshTokenEntitiy = refreshTokenRepository.findById(userId).orElseThrow(()->{
            return new RefresTokenException("No refresh token founded by "+userId);
        });

        String oldRefreshToken = refreshTokenEntitiy.getRefreshToken();
        if(sendedRefreshToken.equals(oldRefreshToken)){
            String newRefreshToken = util.createRefreshToken(userId);
            String accessToken = util.createAccessToken(userId);
            refreshTokenEntitiy.setRefreshToken(newRefreshToken);
            refreshTokenDTO.setRefreshToken(newRefreshToken);
            refreshTokenDTO.setAccessToken(accessToken);
            refreshTokenRepository.save(refreshTokenEntitiy);
        }else throw new RefresTokenException("Refresh token not matched");
        return refreshTokenDTO;
    }
}
