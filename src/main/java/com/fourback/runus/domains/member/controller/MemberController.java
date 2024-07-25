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

/**
 * packageName : com.fourback.runus.member.controller fileName :
 * MemberController author : Yeong-Huns date : 2024-07-22 description :
 * =========================================================== DATE AUTHOR NOTE
 * ----------------------------------------------------------- 2024-07-22
 * Yeong-Huns 최초 생성
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;
	private final MQSender MQSender;
    private EmailService emailService;
	

	@PostMapping("/create")
	public ResponseEntity<ResponseCode> addMember(@RequestBody @Valid CreateMemberRequest request) {
		Member member = memberService.save(request);
		MQSender.sendToDirectExchange("member.create", SendCreateMemberRequest.from(member));
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseCode.MEMBER_CREATED);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<FindMembersResponse>> getAllMembers() {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll());
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<FindMembersResponse> getMemberById(@PathVariable Long id) {
		return ResponseEntity.ok().body(FindMembersResponse.from(memberService.findActiveMemberById(id)));
	}

	@PutMapping("/updateMemberInfo")
	public ResponseEntity<ResponseCode> updateMemberInfo(@RequestBody UpdateMemberRequest request) {
		MQSender.sendToTopicExchange("member.update", request);
		return ResponseEntity.ok().body(ResponseCode.MEMBER_UPDATED);
	}

	@PutMapping("/roleUpdate/{id}")
	public ResponseEntity<ResponseCode> updateMemberRole(@PathVariable Long id) {
		MQSender.sendToTopicExchange("member.update.role", id);
		return ResponseEntity.ok().body(ResponseCode.MEMBER_UPDATED);
	}

	@PutMapping("/updateMemberProfile")
	public ResponseEntity<ResponseCode> updateMemberProfile(@RequestBody UpdateMemberProfileRequest request) {
		MQSender.sendToTopicExchange("member.update.profile", request);
		return ResponseEntity.ok().body(ResponseCode.MEMBER_UPDATED);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ResponseCode> deleteMemberById(@PathVariable Long id) {
		MQSender.sendToTopicExchange("member.delete", SendDeleteMemberRequest.of(id, LocalDateTime.now()));
		return ResponseEntity.ok().body(ResponseCode.MEMBER_DELETED);
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<ResponseCode> deleteAllMembers() {
		MQSender.sendToTopicExchange("member.delete.all", LocalDateTime.now());
		return ResponseEntity.ok().body(ResponseCode.MEMBER_DELETED);
	}

	/* 메인 서버 */
	// 회원 정보 수정
	@PutMapping(value = "/updateMemberInfo/{user-id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseCode> updateMemberInfo(@PathVariable("user-id") Long userId,
			@RequestPart("form") UpdateMemberRequest request,
			@RequestPart(value = "file", required = false) MultipartFile multipartFile) {

		log.info("Received update request for userId: {}", userId);
		log.info("Request details: {}", request);
		Member member = memberService.updateMemberInfo(userId, request, multipartFile);
		return ResponseEntity.ok().body(ResponseCode.MEMBER_UPDATED.withData(member));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		log.info("Forgot password endpoint called with email: {}", email);
		try {
			String tempPassword = memberService.generateTemporaryPassword();
			memberService.updatePassword(email, tempPassword);
			emailService.sendTemporaryPassword(email, tempPassword);
			log.info("Temporary password sent to email: {}", email);
			return ResponseEntity.ok("Temporary password sent to your email.");
		} catch (Exception e) {
			log.error("Error processing forgot password for email: {}", email, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request.");
		}
	}
}
