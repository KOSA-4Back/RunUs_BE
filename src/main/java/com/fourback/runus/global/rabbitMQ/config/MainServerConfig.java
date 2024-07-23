package com.fourback.runus.global.rabbitMQ.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * packageName    : com.fourback.runus.global.rabbitMQ.config
 * fileName       : MainServerConfig
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Configuration
public class MainServerConfig {
    @Bean
    public Queue userCreateQueue(){
        return new Queue("member.create.queue", true);
    }
    @Bean
    public Queue userUpdateQueue(){
        return new Queue("member.update.queue", true);
    }
    @Bean
    public Queue userDeleteQueue(){
        return new Queue("member.delete.queue", true);
    }
    @Bean
    public Queue userDeleteAllQueue(){
        return new Queue("member.delete.all.queue", true);
    }

    @Bean
    public Binding bindingUserCreateQueue(DirectExchange exchange, Queue userCreateQueue){
        return BindingBuilder.bind(userCreateQueue()).to(exchange).with("member.create");
    }
    @Bean
    public Binding bindingUserUpdateQueue(DirectExchange exchange, Queue userUpdateQueue){
        return BindingBuilder.bind(userUpdateQueue()).to(exchange).with("member.update");

    }
    @Bean
    public Binding bindingUserDeleteQueue(DirectExchange exchange, Queue userDeleteQueue){
        return BindingBuilder.bind(userDeleteQueue()).to(exchange).with("member.delete");
    }
    @Bean
    public Binding bindingUserDeleteAllQueue(DirectExchange exchange, Queue userDeleteAllQueue){
        return BindingBuilder.bind(userDeleteAllQueue()).to(exchange).with("member.delete.all");
    }
}
