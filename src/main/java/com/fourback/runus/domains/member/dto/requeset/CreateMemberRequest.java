package com.fourback.runus.domains.member.dto.requeset;

import com.fourback.runus.domains.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

/**
 * packageName    : com.fourback.runus.member.dto.requeset
 * fileName       : CreateMemberRequest
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Builder
public record CreateMemberRequest(

        @Email(message = "이메일 형식이어야 합니다.")
        String email,

        @NotEmpty(message = "닉네임을 입력해 주세요")
        String nickName,

        @NotEmpty(message = "비밀번호를 입력해 주세요")
        String password,

        @NotNull
        LocalDate birth,

        @NotNull
        int height,

        @NotNull
        int weight
) {
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .nickName(nickName)
                .password(password)
                .birth(birth)
                .height(height)
                .weight(weight)
                .build();
    }
}
