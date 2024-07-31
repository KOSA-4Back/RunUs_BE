package com.fourback.runus.domains.running.dto.response;

import java.util.Date;

import com.fourback.runus.domains.running.domain.TodayGoal;

import lombok.Builder;

@Builder
public record StartRunningResponse(
        Long TodayGoalId,
        Long RunTotalInfoId,
        Date today // 추가된 필드
) {
    public TodayGoal toEntity() {
        return TodayGoal.builder()
                .userId(TodayGoalId)
                .goalKm(RunTotalInfoId)
                .build();
    }
}
