package com.fourback.runus.global.rabbitMQ.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.exchange
 * fileName       : DirectConfig
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Configuration
public class DirectExchangeConfig {
    @Bean
    public Queue userCreateQueue(){
        return new Queue("member.create.queue", true);
    }
    @Bean
    public Binding bindingCreateQueue(DirectExchange exchange, Queue userCreateQueue){
        return BindingBuilder.bind(userCreateQueue).to(exchange).with("member.create");
    }
}
