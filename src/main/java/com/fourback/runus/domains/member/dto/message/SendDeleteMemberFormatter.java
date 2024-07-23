package com.fourback.runus.domains.member.dto.message;

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
public record SendDeleteMemberFormatter(long userId, LocalDateTime deletedAt) {
    public static SendDeleteMemberFormatter of(long userId, LocalDateTime deletedAt) {
        return new SendDeleteMemberFormatter(userId, deletedAt);
    }
}
