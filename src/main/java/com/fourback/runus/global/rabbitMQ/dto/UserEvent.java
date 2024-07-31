package com.fourback.runus.global.rabbitMQ.dto;

import lombok.Builder;

/**
 * packageName    : com.fourback.runus.global.rabbitMQ.dto
 * fileName       : UserEvent
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
public record UserEvent(
        String type,
        User user
) {
    public static UserEvent of (String type,User user) {
        return new UserEvent(type, user);
    }
}
