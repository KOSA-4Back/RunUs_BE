package com.fourback.runus.global.rabbitMQ.listener;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.SendDeleteMemberRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberProfileRequest;
import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.global.error.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.config.listener
 * fileName       : MemberListener
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MemberListener {
    private final MemberRepository memberRepository;

    @RabbitListener(queues = "main.member.update.queue")
    @Transactional
    public void handleMemberUpdateMessage(UpdateMemberRequest message) {
        log.info("handleUserUpdateMessage: {}", message);
        memberRepository.findById(message.userId())
                .orElseThrow(() -> new NotFoundException("해당 멤버의 정보수정에 실패하였습니다. "))
                .updateMemberInfo(message);  // 변경 감지 저장
    }

    @RabbitListener(queues = "main.member.update.role.queue")
    @Transactional
    public void handleMemberUpdateProfile(long id) {
        log.info("handleUserUpdateRole");
        memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 멤버의 권한 업데이트에 실패하였습니다."))
                .changeRoleToAdmin();
    }

    @RabbitListener(queues = "main.member.update.profile.queue")
    @Transactional
    public void handleMemberUpdateProfile(UpdateMemberProfileRequest message) {
        log.info("handleUserUpdateProfile");
        memberRepository.findById(message.userId())
                .orElseThrow(() -> new NotFoundException("해당 멤버의 프로필 업데이트에 실패하였습니다."))
                .updateProfileUrl(message.profileUrl());
    }

    @RabbitListener(queues = "main.member.delete.queue")
    @Transactional
    public void handleUserDeleteMessage(SendDeleteMemberRequest message) {
        log.info("deleteUserById: {}", message);
        memberRepository.deleteMember(message);
    }

    @RabbitListener(queues = "main.member.delete.all.queue")
    @Transactional
    public void handleUserDeleteAllMessage(LocalDateTime deletionTime) {
        log.info("deleteAllUserMessage: {}", deletionTime);
        memberRepository.deleteAllMembers(deletionTime);
    }
}
