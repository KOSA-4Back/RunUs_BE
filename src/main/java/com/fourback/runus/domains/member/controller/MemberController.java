package com.fourback.runus.domains.member.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.SendCreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.SendDeleteMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberProfileRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.service.EmailService;
import com.fourback.runus.domains.member.service.MemberService;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import com.fourback.runus.global.rabbitMQ.publisher.MQSender;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.fourback.runus.global.error.errorCode.ResponseCode.MEMBER_UPDATED;

/**
 * packageName    : com.fourback.runus.member.controller
 * fileName       : MemberController
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;


	/* 메인 서버 */
	// 회원 정보 수정
	@PutMapping(value = "/updateMemberInfo/{user-id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseCode> updateMemberInfo(@PathVariable("user-id") Long userId,
			@RequestPart("form") UpdateMemberRequest request,
			@RequestPart(value = "file", required = false) MultipartFile multipartFile) {

		log.info("Received update request for userId: {}", userId);
		log.info("Request details: {}", request);
		Member member = memberService.updateMemberInfo(userId, request, multipartFile);

		return ResponseEntity.ok().body(MEMBER_UPDATED.withData(member.getUserId()));
	}
}
