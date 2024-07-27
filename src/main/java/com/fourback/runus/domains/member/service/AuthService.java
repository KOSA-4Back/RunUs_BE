package com.fourback.runus.domains.member.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourback.runus.domains.member.domain.VerificationCode;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.domains.member.repository.VerificationCodeRepository;
import com.fourback.runus.global.error.exception.CustomBaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


/**
 * packageName    : com.fourback.runus.domains.member.service
 * fileName       : AuthService
 * author         : 김민지
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        김민지              최초 생성
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {
	private final VerificationCodeRepository verificationCodeRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 인증번호 생성 및 저장
    @Transactional
    public String generateAndSaveVerificationCode(String email) {
        // 기존 인증번호 삭제
        verificationCodeRepository.deleteByEmail(email);

        // 새로운 인증번호 생성 및 저장
        String code = RandomStringUtils.randomAlphanumeric(6);
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();
        verificationCodeRepository.save(verificationCode);
        return code;
    }

    // 인증번호 확인
    @Transactional(readOnly = true)
    public boolean verifyCode(String email, String code) {
        log.info("Verifying code for email: {} with code: {}", email, code);
        boolean exists = verificationCodeRepository.findByEmailAndCode(email, code).isPresent();
        log.info("Verification result for email: {} with code: {}: {}", email, code, exists);
        return exists;
    }

    // 인증번호 삭제
    @Transactional
    public void deleteVerificationCode(String email) {
        verificationCodeRepository.deleteByEmail(email);
    }
    
    
    // 비밀번호 변경
    @Transactional
    public void changePassword(String email, String newPassword) {
        var member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomBaseException("User not found with email: " + email));

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}
