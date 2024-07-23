package com.fourback.runus.domains.member.controller;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.CreateMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.dto.response.FindMembersResponse;
import com.fourback.runus.domains.member.dto.response.SendMessageFormatter;
import com.fourback.runus.domains.member.service.MemberService;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final RabbitTemplate rabbitTemplate;
    @PostMapping("/create")
    public ResponseEntity<ResponseCode> addMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = memberService.save(request);
        rabbitTemplate.convertAndSend("member.create", SendMessageFormatter.from(member));
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

    @PutMapping("/roleUpdate/{id}")
    public ResponseEntity<ResponseCode> updateMemberRole(@PathVariable Long id){
        Member member = memberService.changeRoleToAdmin(id);
        rabbitTemplate.convertAndSend("member.update", SendMessageFormatter.from(member) );
        return ResponseEntity.ok().body(ResponseCode.MEMBER_UPDATED);
    }

    @PutMapping("/updateMemberInfo/{id}")
    public ResponseEntity<ResponseCode> updateMemberInfo(@RequestBody UpdateMemberRequest request){
        //Member member = memberService.updateMember(request);
        rabbitTemplate.convertAndSend("member.update", request);
        return ResponseEntity.ok().body(ResponseCode.MEMBER_UPDATED);
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ResponseCode> deleteMemberById(@PathVariable Long id) {
        memberService.deleteById(id);
        rabbitTemplate.convertAndSend("member.delete", id);
        return ResponseEntity.ok().body(ResponseCode.MEMBER_DELETED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseCode> deleteAllMembers() {
        memberService.deleteAllMembers();
        rabbitTemplate.convertAndSend("member.deleteAll", ""); // 매개 변수에 빈 문자열을 넣어줘야 라우팅 키로 인식함
        return ResponseEntity.ok().body(ResponseCode.MEMBER_DELETED);
    }

}
