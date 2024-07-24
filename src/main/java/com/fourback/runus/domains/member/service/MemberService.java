package com.fourback.runus.domains.member.service;

import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.global.error.exception.NotFoundException;
import com.fourback.runus.domains.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final MemberRepository memberRepository;

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

}
