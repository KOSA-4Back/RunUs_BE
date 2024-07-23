package com.fourback.runus.domains.member.dto.message;

import com.fourback.runus.domains.member.domain.Member;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.fourback.runus.member.dto.response
 * fileName       : SendCreateMessageFormat
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Builder
public record SendMessageFormatter(
        long userId,
        String email,
        String nickName,
        LocalDate birth,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SendMessageFormatter from(Member member){
        return SendMessageFormatter.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .birth(member.getBirth())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
