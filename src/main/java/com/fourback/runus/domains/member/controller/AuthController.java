package com.fourback.runus.domains.member.controller;

import static com.fourback.runus.global.error.errorCode.ResponseCode.EXISTS_EMAIL;
import static com.fourback.runus.global.error.errorCode.ResponseCode.EXISTS_NICKNAME;
import static com.fourback.runus.global.error.errorCode.ResponseCode.MEMBER_CREATED;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.LoginRequest;
import com.fourback.runus.domains.member.service.AuthService;
import com.fourback.runus.domains.member.service.EmailService;
import com.fourback.runus.domains.member.service.MemberService;
import com.fourback.runus.global.error.errorCode.ResponseCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final EmailService emailService;


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


    // 비밀번호 찾기
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        log.info("====>>>>>>>>>> Forgot password endpoint called with email: {}", email);
        try {
            // 인증번호 생성 및 저장
            String tempPassword = authService.generateAndSaveVerificationCode(email);
            // 인증번호 이메일로 발송
            emailService.sendTemporaryPassword(email, tempPassword);
            log.info("Temporary password sent to email: {}", email);

            return ResponseEntity.ok("Temporary password sent to your email.");

        } catch (Exception e) {
            log.error("Error processing forgot password for email: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request.");
        }
    }

    // 인증번호 확인
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        log.info("Verify code endpoint called with email: {} and code: {}", email, code);
        boolean isValid = authService.verifyCode(email, code);
        if (isValid) {
            authService.deleteVerificationCode(email);
            return ResponseEntity.ok("Verification successful.");
        } else {
            return ResponseEntity.status(400).body("Invalid verification code.");
        }
    }
    
    
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");
        log.info("Change password endpoint called with email: {}", email);
        try {
            authService.changePassword(email, newPassword);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (Exception e) {
            log.error("Error changing password for email: {}", email, e);
            return ResponseEntity.status(500).body("Error changing password.");
        }
    }
}
