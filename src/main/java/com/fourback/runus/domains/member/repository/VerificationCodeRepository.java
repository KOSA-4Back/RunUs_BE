package com.fourback.runus.domains.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourback.runus.domains.member.domain.VerificationCode;


/**
 * packageName    : com.fourback.runus.domains.member.repository
 * fileName       : VerificationCodeRepository
 * author         : 김민지
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        김민지              최초 생성
 */
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
