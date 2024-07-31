package com.fourback.runus.domains.member.service;

import static com.fourback.runus.global.error.errorCode.ResponseCode.PASSWORD_INVALID;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.LoginRequest;
import com.fourback.runus.domains.member.dto.requeset.MemberChangePasswordRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.dto.response.MemberInfoTodayRunResponse;
import com.fourback.runus.domains.member.dto.response.TodayTotalDistanceCalDto;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.domains.running.domain.RunTotalInfos;
import com.fourback.runus.domains.running.domain.TodayGoal;
import com.fourback.runus.domains.running.dto.response.FindTodayGoalResponse;
import com.fourback.runus.domains.running.repository.RunTotalInfosRepository;
import com.fourback.runus.domains.running.repository.TodayGoalRepository;
import com.fourback.runus.global.amazon.service.S3Service;
import com.fourback.runus.global.error.exception.CustomBaseException;
import com.fourback.runus.global.error.exception.NotFoundException;
import com.fourback.runus.global.redis.dto.GetTokenResponse;
import com.fourback.runus.global.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

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
 * 2024-07-22        김민지            회원가입/로그인 기능 구현
 * 2024-07-24        김은정            회원가입/로그인 기능 메서드 수정
 * 2024-07-26        김은정            회원정보 수정 메서드 수정
 * 2024-07-26        김영훈            로그인시 반환값 수정
 * 2024-07-30        김은정            프로필 정보 조회 메서드 생성
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TodayGoalRepository todayGoalRepository;
    private final RunTotalInfosRepository runTotalInfosRepository;

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


    // 조회 (userId 기준)
    public Member findById(Long id) {
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
            try {
                imageUrl = s3Service.uploadFile(multipartFile);
                log.debug("====>>>>>>>>>> 업로드된 이미지 URL: " + imageUrl);
            } catch (Exception e) {
                log.error("이미지 업로드 실패", e);
                throw new RuntimeException("이미지 업로드 실패", e);
            }
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

        log.info("저장된 멤버: " + saveMember);
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


    // 회원 정보 수정
    @Transactional
    public FindMembersResponse updateMemberInfo(Long userId, UpdateMemberRequest request,
        MultipartFile multipartFile) {
        log.info("Updating member info for userId: {}", userId);

        // 기존 멤버 정보 조회 및 존재 여부 확인
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("존재하지 않은 유저입니다."));
        log.info("Found member: {}", member);

        // 이미지 S3에 저장
        String imageUrl = member.getProfileUrl();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // 기존 파일 이름 추출
                String existingFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                try {
                    imageUrl = s3Service.updateFile(multipartFile, existingFileName);

                } catch (IOException e) {
                    log.error("Failed to update file", e);
                    throw new RuntimeException("이미지 업데이트 실패", e);
                }

            } else {
                imageUrl = s3Service.uploadFile(multipartFile);
            }

            log.debug("Updated image URL: {}", imageUrl);
        }

        // 업데이트된 정보를 반영
        member.updateMemberInfo(request, imageUrl);
        Member updatedMember = memberRepository.save(member);  // 변경사항 저장
        log.info("Updated member: {}", updatedMember);

        return FindMembersResponse.from(member);
    }


    // 비밀번호 변경
    @Transactional
    public void changePassword(Long userId,
        MemberChangePasswordRequest memberChangePasswordRequest) {

        // 유저 확인
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("존재하지 않은 유저입니다."));

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(memberChangePasswordRequest.password(), member.getPassword())) {
            throw new CustomBaseException(PASSWORD_INVALID);
        }

        // 비밀번호 변경
        member.setPassword(passwordEncoder.encode(memberChangePasswordRequest.changePassword()));
        memberRepository.save(member);
    }

    
    // 프로필 정보 조회
    // members, 오늘 목표, 오늘 달리기 정보 조회
    public MemberInfoTodayRunResponse memberInfoTodayGoalRun(Long userId) {

        // 유저 확인
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("존재하지 않은 유저입니다."));

        // 오늘 목표
        TodayGoal todayGoal = todayGoalRepository.findByUserIdAndToday(userId, LocalDate.now());

        // 오늘 달리기 정보
        List<RunTotalInfos> runTotalInfosList =
            runTotalInfosRepository.findByTodayGoalId(todayGoal.getTodayGoalId());
        long totalDistance = runTotalInfosList.stream()
            .mapToLong(RunTotalInfos::getTotalDistance)
            .sum();
        long totalCalories = runTotalInfosList.stream().
            mapToLong(RunTotalInfos::getTotalCalories)
            .sum();

        TodayTotalDistanceCalDto TodayTotalDistanceCalDto =
            new TodayTotalDistanceCalDto(totalDistance, totalCalories);

        return MemberInfoTodayRunResponse.of(FindMembersResponse.from(member),
                FindTodayGoalResponse.from(todayGoal), TodayTotalDistanceCalDto);
    }
}
