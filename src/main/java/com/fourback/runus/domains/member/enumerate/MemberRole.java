package com.fourback.runus.domains.member.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.fourback.runus.member.enumerate
 * fileName       : Role
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("일반 사용자"),
    ADMIN("관리자");

    private final String koreanName;
}
