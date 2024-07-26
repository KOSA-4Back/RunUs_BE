package com.fourback.runus.global.security.config;

import com.fourback.runus.global.redis.service.RedisAuthHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
public class RedisLogoutHandler implements LogoutHandler {
    private final RedisAuthHandler redisAuthHandler;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String userId = request.getHeader("userId");
        if (userId != null) redisAuthHandler.logout(userId);
    }
}
