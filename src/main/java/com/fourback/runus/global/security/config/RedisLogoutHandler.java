package com.fourback.runus.global.security.config;

import com.fourback.runus.global.redis.service.RedisAuthHandler;
import com.fourback.runus.global.security.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.fourback.runus.global.security.config
 * fileName       : LogoutHandler
 * author         : Yeong-Huns
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        Yeong-Huns       최초 생성
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class RedisLogoutHandler implements LogoutHandler {
    private final RedisAuthHandler redisAuthHandler;
    private final JwtProvider jwtProvider;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("Authorization");
        token = token.substring(7); //Bearer+공백한칸 제거
        log.info("Attempting to logout. Token: {}", token);
        long userId = jwtProvider.getUserId(token); // 은정님이 만들어 둔 토큰 파서
        log.info("User ID: {}", userId);
        redisAuthHandler.logout(userId);
    }
}
