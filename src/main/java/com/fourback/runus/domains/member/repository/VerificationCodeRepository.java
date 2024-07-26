package com.fourback.runus.domains.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourback.runus.domains.member.domain.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
