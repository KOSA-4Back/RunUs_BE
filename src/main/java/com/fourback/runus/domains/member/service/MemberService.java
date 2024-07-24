package com.fourback.runus.domains.member.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.enumerate.MemberRole;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.fourback.runus.member.repository
 * fileName       : MemberService
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Service
@RequiredArgsConstructor
public class MemberService {
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Member save(CreateMemberRequest request) {
        return memberRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<FindMembersResponse> findAll() {
        return memberRepository.findAllActiveMembers()
                .stream()
                .map(FindMembersResponse::from)
                .toList();
    }
    @Transactional(readOnly = true)
    public Member findActiveMemberById(Long id) {
        return memberRepository.findActiveMemberById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public Member changeRoleToAdmin(long id) {
        return findById(id).changeRoleToAdmin(); // 변경 감지 저장 -> 채팅 서버로 전달하기 위해 Member 반환
    }

//    @Transactional
//    public Member updateMember(UpdateMemberRequest request) {
//        return findById(request.userId()).updateMemberInfo(request); // JPA 변경 감지 저장 -> 채팅 서버로 전달하기 위해 Member 반환
//    }

    @Transactional
    public void deleteAllMembers(){
        memberRepository.deleteAll();
    }

    private Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(()->new NotFoundException("Id: " + id + ", 해당 Id의 멤버 조회 실패"));
    }
    
    // 회원가입
    public Member registerMember(Member member) {
        logger.info("Registering member with email: {}", member.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(MemberRole.USER);
        Member savedMember = memberRepository.save(member);
        logger.info("Member registered with email: {}", savedMember.getEmail());
        return savedMember;
    }

    // 이메일(아이디)로 조회
    public Member findByEmail(String email) {
        logger.info("Finding member by email: {}", email);
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("Member not found with email: {}", email);
            return new UsernameNotFoundException("존재하지 않는 아이디입니다!");
        });
    }

    // 이메일 중복 확인
    public boolean isEmailTaken(String email) {
        logger.info("Checking if email is taken: {}", email);
        return memberRepository.findByEmail(email).isPresent();
    }

}
