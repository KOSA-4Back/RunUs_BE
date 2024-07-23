package com.fourback.runus.global.rabbitMQ.config.listener;

import com.fourback.runus.domains.member.dto.requeset.UpdateMemberProfileRequest;
import com.fourback.runus.domains.member.dto.response.ReceiveMemberUpdateFormatter;
import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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

    @RabbitListener(queues = "member.update.queue")
    public void handleMemberUpdateMessage(ReceiveMemberUpdateFormatter message) {
        log.info("handleUserUpdateMessage: {}", message);
        memberRepository.findById(message.id())
                .orElseThrow(()->new NotFoundException("해당 멤버의 정보수정에 실패하였습니다. "))
                .updateMemberInfo(message);  // 변경 감지 저장
    }

    @RabbitListener(queues = "member.update.queue")
    public void handleMemberUpdateProfile(UpdateMemberProfileRequest message){
        log.info("handleUserUpdateProfile");
        memberRepository.findById(message.id())
                .orElseThrow(()->new NotFoundException("해당 멤버의 프로필 업데이트에 실패하였습니다."))
                .updateProfileUrl(message.profileUrl());
    }

    @RabbitListener(queues = "member.delete.queue")
    public void handleUserDeleteMessage(long id) {
        log.info("deleteUserById: {}", id);
        memberRepository.deleteById(id);
    }

    @RabbitListener(queues = "member.delete.all.queue")
    public void handleUserDeleteAllMessage(String message) {
        log.info("deleteAllUserMessage: {}", message);
        memberRepository.deleteAll();
    }
}
