package com.fourback.runus.domains.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.CustomUserDetails;
import com.fourback.runus.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );

        return new CustomUserDetails(member);
    }
}
