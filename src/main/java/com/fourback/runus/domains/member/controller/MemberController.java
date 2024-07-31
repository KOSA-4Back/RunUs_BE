package com.fourback.runus.domains.member.controller;

import static com.fourback.runus.global.error.errorCode.ResponseCode.MEMBER_UPDATED;

import com.fourback.runus.domains.member.dto.requeset.MemberChangePasswordRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.dto.response.MemberInfoTodayRunResponse;
import com.fourback.runus.domains.member.service.MemberService;
import com.fourback.runus.domains.running.dto.response.RunTotalInfosSummaryWith;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * packageName    : com.fourback.runus.domains.member.controller
 * fileName       : MemberController
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        김영훈             최초 생성
 * 2024-07-23        김민지             회원정보 수정 메서드 추가
 * 2024-07-26        김은정             회원정보 수정 메서드 수정/비밀번호 변경 메서드 수정
 * 2024-07-30        김은정             조회 (userId 기준 조회) - members, 오늘 목표, 오늘 달리기 정보 /당일 이전 기준 2개만 조회
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;


    // 조회 (userId 기준 조회), members 만 조회
    @GetMapping("/info/{user-id}")
    public ResponseEntity<Object> memberInfo(@PathVariable("user-id") Long userId) {
        FindMembersResponse findMembersResponse = FindMembersResponse.from(
            memberService.findById(userId));

        return ResponseEntity.ok().body(findMembersResponse);
    }

    // 조회 (userId 기준 조회) / members, 오늘 목표, 오늘 달리기 정보
    @GetMapping("/info/today/{user-id}")
    public ResponseEntity<Object> memberInfoTodayGoalRun(@PathVariable("user-id") Long userId) {
        MemberInfoTodayRunResponse memberInfoTodayRunResponse =
            memberService.memberInfoTodayGoalRun(userId);

        return ResponseEntity.ok().body(memberInfoTodayRunResponse);
    }


    /* 메인 서버 */
    // 회원 정보 수정
    @PutMapping(value = "/updateMemberInfo/{user-id}", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseCode> updateMemberInfo(@PathVariable("user-id") Long userId,
        @RequestPart("form") UpdateMemberRequest request,
        @RequestPart(value = "file", required = false) MultipartFile multipartFile) {

        log.info("Received update request for userId: {}", userId);
        log.info("Request details: {}", request);
        FindMembersResponse findMembersResponse = memberService.updateMemberInfo(userId, request,
            multipartFile);

        return ResponseEntity.ok().body(MEMBER_UPDATED.withData(findMembersResponse));
    }


    // 비밀번호 변경
    @PutMapping("/change-password/{user-id}")
    public ResponseEntity<String> changePassword(@PathVariable("user-id") Long userId,
        @RequestBody MemberChangePasswordRequest memberChangePasswordRequest) {

        memberService.changePassword(userId, memberChangePasswordRequest);

        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }


    // 조회 (프로필 조회 활동 부분/당일 이전 기준 2개만 조회)
    @GetMapping("/active/{user-id}")
    public ResponseEntity<List<RunTotalInfosSummaryWith>> memberInfoActive(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(memberService.memberInfoActive(userId));
    }
}
