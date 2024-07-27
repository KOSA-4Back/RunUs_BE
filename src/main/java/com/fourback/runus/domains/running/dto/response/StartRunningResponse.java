package com.fourback.runus.domains.running.dto.response;

import com.fourback.runus.domains.running.domain.TodayGoal;
import lombok.Builder;


/**
 * packageName    : com.fourback.runus.domains.running.dto.response
 * fileName       : StartRunningResponse
 * author         : 김은정
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        김은정           최초 생성
 */
@Builder
public record StartRunningResponse(
        Long TodayGoalId,
        Long RunTotalInfoId
) {
    public TodayGoal toEntity() {
        return TodayGoal.builder()
                .userId(TodayGoalId)
                .goalKm(RunTotalInfoId)
                .build();
    }
}
