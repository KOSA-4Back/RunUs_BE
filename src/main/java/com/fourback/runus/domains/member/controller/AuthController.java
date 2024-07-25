package com.fourback.runus.domains.member.controller;

import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.LoginRequest;
import com.fourback.runus.domains.member.service.MemberService;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.fourback.runus.global.error.errorCode.ResponseCode.*;

@Log4j2
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;


    // 회원가입
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseCode> register(
        @Valid @RequestPart("form") CreateMemberRequest createMemberRequest,
        @RequestPart(value = "file", required = false) MultipartFile multipartFile) {

        log.info("====>>>>>>>>>> into register");

        // 저장
        Long saveUserId = memberService.registerMember(createMemberRequest, multipartFile);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MEMBER_CREATED.withData(saveUserId));
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {

        log.info("====>>>>>>>>>> into login");
        String token = memberService.login(loginRequest);
        Map<String, String> response = new HashMap<>();
        response.put("Token", token);

        return ResponseEntity.ok(response);
    }


    // 이메일 중복검사
    @GetMapping("/check-email")
    public ResponseEntity<Object> checkEmail(@RequestParam("email") String email) {

        log.info("Check email endpoint called with email: {}", email);
        boolean isTaken = memberService.isEmailTaken(email);

        if(isTaken) {
            return ResponseEntity.ok().body(EXISTS_EMAIL);

        } else {
            return ResponseEntity.ok().body(isTaken);
        }
    }


    // 닉네임 중복검사
    @GetMapping("/check-nickname")
    public ResponseEntity<Object> checkNickName(@RequestParam("nickname") String nickname) {

        log.info("Check nickname endpoint called with nickname: {}", nickname);
        boolean isTaken = memberService.isNickNameTaken(nickname);

        if(isTaken) {
            return ResponseEntity.ok().body(EXISTS_NICKNAME);

        } else {
            return ResponseEntity.ok().body(isTaken);
        }
    }
}
