package com.fourback.runus.global.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.fourback.runus.global.security.config
 * fileName       : RedisAuthService
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
public class RedisAuthHandler {
    private final RedisUserService redisUserService;

    public void login(long userId, String token){
        redisUserService.saveUserSession(userId, token);
    }

    public void logout(String userId){
        redisUserService.deleteUserSession(userId);
    }

    public void updateConnectionStatus(String userId , boolean status){
        redisUserService.updateUserConnectionStatus(userId, status);
    }
}
