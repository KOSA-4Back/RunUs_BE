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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourback.runus.domains.member.dto.requeset.AuthChangePasswordRequest;
import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.LoginRequest;
import com.fourback.runus.domains.member.service.AuthService;
import com.fourback.runus.domains.member.service.EmailService;
import com.fourback.runus.domains.member.service.MemberService;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import com.fourback.runus.global.redis.dto.GetTokenResponse;
import com.fourback.runus.global.redis.service.RedisAuthHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * packageName    : com.fourback.runus.domains.member.controller
 * fileName       : AuthController
 * author         : 김민지
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        김민지            최초 생성
 * 2024-07-24        김은정            로그인/회원가입 관련 메서드 수정
 * 2024-07-24        김민지            회원 정보 수정 및 비밀번호 찾기/변경 메서드 생성 및 수정
 * 2024-07-26        김은정            회원 정보 수정 및 비밀번호 변경 메서드 수정
 * 2024-07-26        김영훈            로그인 시 redis에 저장되게 수정
 * 2024-07-31        김영훈            USER-EVENT 생성
 */
@Log4j2
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    private final EmailService emailService;
    private final RedisAuthHandler redisAuthHandler; //레디스 저장을 위한 주입 by Yh

    private final ObjectMapper objectMapper;

    // 회원가입
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseCode> register(
        @Valid @RequestPart("form") String createMemberRequestJson,
        @RequestPart(value = "file", required = false) MultipartFile multipartFile) {

    	log.info("넘어는오니? : {}", createMemberRequestJson);
    	
    	CreateMemberRequest createMemberRequest;
        try {
            createMemberRequest = objectMapper.readValue(createMemberRequestJson, CreateMemberRequest.class);
            log.info(createMemberRequest + "");
        } catch (JsonProcessingException e) {
            log.error("JSON parsing error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 적절한 에러 처리
        }
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
        GetTokenResponse tokenResponse = memberService.login(loginRequest);
        long userId = tokenResponse.userId();
        String token = tokenResponse.token();
        Map<String, String> response = new HashMap<>();
        response.put("Token", token);
        response.put("UserId", String.valueOf(userId)); // Add UserId to the response

        redisAuthHandler.login(userId, token); // 레디스 저장
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

            return ResponseEntity.ok("Verify-code sent to your email.");

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
    
    
    // 로그인 화면의 비밀번호 찾기 후 변경
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody AuthChangePasswordRequest authChangePasswordRequest) {
        String email = authChangePasswordRequest.email();
        String newPassword = authChangePasswordRequest.changePassword();

        log.info("Change password endpoint called with email: {}", email);
        authService.changePassword(email, newPassword);

        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}
