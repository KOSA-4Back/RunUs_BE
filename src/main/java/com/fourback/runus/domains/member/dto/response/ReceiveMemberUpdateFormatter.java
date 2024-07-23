package com.fourback.runus.domains.member.dto.response;

import com.fourback.runus.domains.member.domain.Member;

import java.time.LocalDate;

/**
 * packageName    : com.fourback.runus.domains.member.dto.response
 * fileName       : ReceiveMemberUpdateFormatter
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
public record ReceiveMemberUpdateFormatter(
        long id,
        String nickName,
        LocalDate birth,
        int height,
        int weight
) {
}
