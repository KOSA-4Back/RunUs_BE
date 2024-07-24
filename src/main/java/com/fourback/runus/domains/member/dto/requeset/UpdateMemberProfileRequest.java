package com.fourback.runus.domains.member.dto.requeset;

/**
 * packageName    : com.fourback.runus.domains.member.dto.requeset
 * fileName       : UpdateMemberProfileRequest
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
public record UpdateMemberProfileRequest(
        long userId,
        String profileUrl
) {
}
