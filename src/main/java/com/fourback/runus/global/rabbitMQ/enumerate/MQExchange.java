package com.fourback.runus.global.rabbitMQ.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.enumerate
 * fileName       : ExchangeType
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Getter
@RequiredArgsConstructor
public enum MQExchange {
    DIRECT("directExchange"),
    FANOUT("fanoutExchange"),
    TOPIC("topicExchange");

    private final String exchangeName;
}
