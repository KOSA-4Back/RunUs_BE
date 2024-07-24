package com.fourback.runus.domains.members.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fourback.runus.domains.members.domain.Member;
import com.fourback.runus.domains.members.repository.MemberRepository;

@Service
public class MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member registerMember(Member member) {
        logger.info("Registering member with email: {}", member.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        Member savedMember = memberRepository.save(member);
        logger.info("Member registered with email: {}", savedMember.getEmail());
        return savedMember;
    }

    public Member findByEmail(String email) {
        logger.info("Finding member by email: {}", email);
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("Member not found with email: {}", email);
            return new UsernameNotFoundException("존재하지 않는 아이디입니다!");
        });
    }

    public boolean isEmailTaken(String email) {
        logger.info("Checking if email is taken: {}", email);
        return memberRepository.findByEmail(email).isPresent();
    }
}
