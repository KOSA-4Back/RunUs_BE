package com.fourback.runus.domains.member.service;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.LoginRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.global.error.exception.CustomBaseException;
import com.fourback.runus.global.error.exception.NotFoundException;
import com.fourback.runus.global.redis.dto.GetTokenResponse;
import com.fourback.runus.global.security.provider.JwtProvider;
import com.fourback.runus.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.fourback.runus.global.error.errorCode.ResponseCode.PASSWORD_INVALID;

/**
 * packageName    : com.fourback.runus.member.service
 * fileName       : MemberService
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {
	
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final JwtProvider jwtProvider;



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
    public void deleteAllMembers() {
        memberRepository.deleteAll();
    }

    private Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id: " + id + ", 해당 Id의 멤버 조회 실패"));
    }



    // 메인서버 회원가입
    @Transactional
    public Long registerMember(CreateMemberRequest createMemberRequest,
                               MultipartFile multipartFile) {

        log.info("====>>>>>>>>>> MemberService::registerMember");

        // 이미지 S3에 저장
        String imageUrl = "";
        if (multipartFile != null && !multipartFile.isEmpty()) {
            imageUrl = s3Service.uploadFile(multipartFile);
            log.debug("====>>>>>>>>>> s3Service {}", s3Service.uploadFile(multipartFile));

        }

        Member saveMember = memberRepository.save(Member.builder()
                .email(createMemberRequest.email())
                .nickName(createMemberRequest.nickName())
                .password(passwordEncoder.encode(createMemberRequest.password()))
                .birth(createMemberRequest.birth())
                .profileUrl(imageUrl)
                .height(createMemberRequest.height())
                .weight(createMemberRequest.weight())
                .build());

        // 저장
        return saveMember.getUserId();
    }


    // 이메일 중복 확인
    public boolean isEmailTaken(String email) {
        log.info("Checking if email is taken: {}", email);
        return memberRepository.existsByEmail(email);
    }


    // 닉네임 중복 확인
    public boolean isNickNameTaken(String nickname) {
        log.info("Checking if nickname is taken: {}", nickname);
        return memberRepository.existsByNickName(nickname);
    }

    
    // 로그인
    public GetTokenResponse login(LoginRequest loginRequest) {

        // 이메일 조회
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new CustomBaseException(PASSWORD_INVALID);
        }

        // JWT 토큰 생성
        String jwtToken = jwtProvider.createToken(member.getEmail(), member.getUserId(), String.valueOf(member.getRole()));
        return GetTokenResponse.of(member.getUserId(), jwtToken);
    }
    
    
    // 프로필 정보 수정
    @Transactional
    public Member updateMemberInfo(Long userId, UpdateMemberRequest request, MultipartFile multipartFile) {
        log.info("Updating member info for userId: {}", userId);
        
//        Long authenticatedUserId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!authenticatedUserId.equals(request.userId())) {
//            throw new CustomBaseException(ResponseCode.UNAUTHORIZED_ACTION);
//        }
        
        // 이미지 S3에 저장
//        String imageUrl = "";
//        if (multipartFile != null && !multipartFile.isEmpty()) {
//            imageUrl = s3Service.uploadFile(multipartFile);
//            log.debug("====>>>>>>>>>> s3Service {}", s3Service.updateFile(multipartFile));
//
//        }

        Member member = findById(userId);
        log.info("Found member: {}", member);
        member.updateMemberInfo(request);
        Member updatedMember = memberRepository.save(member);  // 변경사항 저장
        log.info("Updated member: {}", updatedMember);
        return updatedMember;
    }
    
    // 인증번호 생성
    public String generateTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    // 비밀 번호 업데이트
    @Transactional
    public void updatePassword(String email, String tempPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        member.setPassword(passwordEncoder.encode(tempPassword));
        memberRepository.save(member);
    }
}
