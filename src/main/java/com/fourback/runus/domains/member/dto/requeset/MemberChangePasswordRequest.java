package com.fourback.runus.domains.member.dto.requeset;


/**
 * packageName    : com.fourback.runus.domains.member.dto.requeset
 * fileName       : LoginRequest
 * author         : 김은정
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        김은정             최초 생성
 */
public record MemberChangePasswordRequest(
    String password,
    String changePassword
){}
