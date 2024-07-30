package com.fourback.runus.global.rabbitMQ.publisher;

import com.fourback.runus.global.rabbitMQ.enumerate.MQExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.sender
 * fileName       : MessageSender
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Component
public class MQSender { // rabbitTemplate 캡슐화
    private final RabbitTemplate rabbitTemplate;

    public void sendToDirectExchange(String routingKey, Object message){
        rabbitTemplate.convertAndSend(MQExchange.DIRECT.getExchangeName(), routingKey, message);
    }

//    public void sendToFanoutExchange(Object message){
//        rabbitTemplate.convertAndSend(MQExchange.FANOUT.getExchangeName(), "", message);
//    }
//
//    public void sendToTopicExchange(String routingKey, Object message){
//        rabbitTemplate.convertAndSend(MQExchange.TOPIC.getExchangeName(), routingKey, message);
//    }
}
