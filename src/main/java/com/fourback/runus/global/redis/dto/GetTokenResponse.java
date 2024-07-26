package com.fourback.runus.global.redis.dto;

import lombok.Builder;

/**
 * packageName    : com.fourback.runus.global.redis.dto
 * fileName       : GetTokenResponse
 * author         : Yeong-Huns
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        Yeong-Huns       최초 생성
 */
@Builder
public record GetTokenResponse (long userId, String token){
    public static GetTokenResponse of(long userId, String token){
        return new GetTokenResponse(userId, token);
    }
}
