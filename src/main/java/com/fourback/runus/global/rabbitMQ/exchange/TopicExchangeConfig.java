package com.fourback.runus.global.rabbitMQ.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.exchange
 * fileName       : TopicConfig
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Configuration
public class TopicExchangeConfig {
    @Bean
    public Queue userDeleteQueue(){
        return new Queue("main.member.delete.queue", true);
    }
    @Bean
    public Queue userUpdateQueue(){
        return new Queue("main.member.update.queue", true);
    }
    @Bean
    public Queue userUpdateRoleQueue(){
        return new Queue("main.member.update.role.queue", true);
    }
    @Bean
    public Queue userUpdateProfileQueue(){
        return new Queue("main.member.update.profile.queue", true);
    }
    @Bean
    public Queue userDeleteAllQueue(){
        return new Queue("main.member.delete.all.queue", true);
    }

    @Bean

    public Binding bindingDeleteQueue(TopicExchange exchange, @Qualifier("userDeleteQueue") Queue userDeleteQueue){
       return BindingBuilder.bind(userDeleteQueue).to(exchange).with("member.delete");
    }

    @Bean
    public Binding bindingUpdateQueue(TopicExchange exchange, @Qualifier("userUpdateQueue") Queue userUpdateQueue){
        return BindingBuilder.bind(userUpdateQueue).to(exchange).with("member.update");
    }
    @Bean
    public Binding bindingUpdateRoleQueue(TopicExchange exchange, @Qualifier("userUpdateRoleQueue") Queue userUpdateRoleQueue){
        return BindingBuilder.bind(userUpdateRoleQueue).to(exchange).with("member.update.role");
    }
    @Bean
    public Binding bindingUpdateProfileQueue(TopicExchange exchange, @Qualifier("userUpdateProfileQueue") Queue userUpdateProfileQueue){
            return BindingBuilder.bind(userUpdateProfileQueue).to(exchange).with("member.update.profile");
    }

    @Bean
    public Binding bindingDeleteAllQueue(TopicExchange exchange, @Qualifier("userDeleteAllQueue") Queue userDeleteAllQueue){
        return BindingBuilder.bind(userDeleteAllQueue).to(exchange).with("member.delete.all");
    }
}
    
