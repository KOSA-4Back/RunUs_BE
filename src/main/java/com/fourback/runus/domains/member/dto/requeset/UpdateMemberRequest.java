package com.fourback.runus.domains.member.dto.requeset;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

/**
 * packageName    : com.fourback.runus.member.dto.requeset
 * fileName       : UpdateMemberRequest
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
public record UpdateMemberRequest(
        @NotEmpty
        long userId,
        @NotEmpty
        String nickName,
        @NotEmpty
        LocalDate birth,
        @NotEmpty
        int height,
        @NotEmpty
        int weight
) {
}
