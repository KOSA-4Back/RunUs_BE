package com.fourback.runus.global.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * packageName    : com.fourback.runus.global.security.config
 * fileName       : RedisUserService
 * author         : Yeong-Huns
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        Yeong-Huns       최초 생성
 */
@Service
public class RedisUserService {
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUserSession(long userId, String token){
        String key = "member:"+userId;
        redisTemplate.opsForHash().put(key, "Token", token);
        redisTemplate.opsForHash().put(key, "ConnectionStatus", "false");
        redisTemplate.expire(key, Duration.ofHours(24)); // 세션 만료시간 설정
    }

    public String getUserToken(String userId){
        String key = "member:"+userId;
        return (String) redisTemplate.opsForHash().get(key, "Token");
    }

    public String getUserConnectionStatus(String userId){
        String key = "member:"+userId;
        return (String) redisTemplate.opsForHash().get(key, "ConnectionStatus");
    }

    public void updateUserConnectionStatus(String userId, boolean connectionStatus){
        redisTemplate.opsForHash().put("member:"+userId, connectionStatus, connectionStatus ? "true" : "false");
    }

    public void deleteUserSession(String userId){
        redisTemplate.delete("member:"+userId);
    }

}
