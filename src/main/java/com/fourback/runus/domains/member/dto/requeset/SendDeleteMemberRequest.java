package com.fourback.runus.domains.member.dto.requeset;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * packageName    : com.fourback.runus.domains.member.dto.message
 * fileName       : SendDeleteMemberFormatter
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
@Builder
public record SendDeleteMemberRequest(long userId, LocalDateTime deletedAt) {
    public static SendDeleteMemberRequest of(long userId, LocalDateTime deletedAt) {
        return new SendDeleteMemberRequest(userId, deletedAt);
    }
}
