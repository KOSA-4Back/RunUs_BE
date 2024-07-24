package com.fourback.runus.domains.member.dto.response;

import com.fourback.runus.domains.member.domain.Member;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.fourback.runus.member.dto.response
 * fileName       : FindAllMembersResponse
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Builder
public record FindMembersResponse(
        long userId,
        String email,
        String nickName,
        LocalDate birth,
        String profileUrl,
        int height,
        int weight,
        String role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static FindMembersResponse from(Member member) {
        return FindMembersResponse.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .birth(member.getBirth())
                .profileUrl(member.getProfileUrl())
                .height(member.getHeight())
                .weight(member.getWeight())
                .role(member.getRole().toString())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
