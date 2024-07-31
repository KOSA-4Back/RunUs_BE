package com.fourback.runus.global.rabbitMQ.dto;

import com.fourback.runus.domains.member.domain.Member;
import lombok.Builder;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.dto
 * fileName       : UserEvent
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@Builder
public record User(
        long id,
        String name,
        String profileUrl
) {
    public static User from(Member member){
        return User.builder()
                .id(member.getUserId())
                .name(member.getNickName())
                .profileUrl(member.getProfileUrl())
                .build();
    }
}
