package com.fourback.runus.domains.running.dto.request;

import com.fourback.runus.domains.running.domain.TodayGoal;
import lombok.Builder;


/**
 * packageName    : com.fourback.runus.domains.running.dto.request
 * fileName       : StartRunningRequest
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            id 성 long -> Long 수정
 */
@Builder
public record StartRunningRequest(
        Long userId,
        long goalKm
) {
    public TodayGoal toEntity() {
        return TodayGoal.builder()
                .userId(userId)
                .goalKm(goalKm)
                .build();
    }
}
