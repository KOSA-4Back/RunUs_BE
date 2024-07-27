package com.fourback.runus.domains.running.dto.response;

import com.fourback.runus.domains.running.domain.RunTotalInfos;
import com.fourback.runus.domains.running.domain.TodayGoal;
import java.time.LocalDateTime;
import lombok.Builder;


/**
 * packageName    : com.fourback.runus.domains.running.dto.response
 * fileName       : EndRunningResponse
 * author         : 김은정
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        김은정           최초 생성
 */
@Builder
public record EndRunningResponse(
    Long totalInfoId,
    Long todayGoalId,
    Long userId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    long totalDistance,
    long totalCalories,
    String totalTime,
    LocalDateTime deletedAt
) {
    public static EndRunningResponse from(RunTotalInfos runTotalInfos) {
        return EndRunningResponse.builder()
            .totalInfoId(runTotalInfos.getTotalInfoId())
            .todayGoalId(runTotalInfos.getTodayGoalId())
            .userId(runTotalInfos.getUserId())
            .startTime(runTotalInfos.getStartTime())
            .endTime(runTotalInfos.getEndTime())
            .totalDistance(runTotalInfos.getTotalDistance())
            .totalCalories(runTotalInfos.getTotalCalories())
            .totalTime(runTotalInfos.getTotalTime())
            .deletedAt(runTotalInfos.getDeletedAt())
            .build();
    }
}
