package com.fourback.runus.domains.members.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourback.runus.domains.members.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}