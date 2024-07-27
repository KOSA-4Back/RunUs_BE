package com.fourback.runus.domains.member.dto.requeset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * packageName    : com.fourback.runus.domains.member.dto.requeset
 * fileName       : LoginRequest
 * author         : 김은정
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        김은정             최초 생성
 */
@Builder
public record LoginRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password) {

    public static LoginRequest of(String email, String password) {
        return new LoginRequest(email, password);
    }
}
