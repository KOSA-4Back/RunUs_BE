package com.fourback.runus.domains.member.dto.requeset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LoginRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password) {

    public static LoginRequest of(String email, String password) {
        return new LoginRequest(email, password);
    }
}
