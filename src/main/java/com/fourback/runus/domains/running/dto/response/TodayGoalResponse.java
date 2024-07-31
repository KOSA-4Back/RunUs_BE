package com.fourback.runus.domains.running.dto.response;

import java.time.LocalDate;
import java.util.Date;

import com.fourback.runus.domains.running.domain.TodayGoal;

import lombok.Builder;

@Builder
public record TodayGoalResponse(
        Long todayGoalId,
        LocalDate today, // 추가된 필드
        long goalKm
) {
    public static TodayGoalResponse from(TodayGoal todayGoal) {
        return TodayGoalResponse.builder()
        		.todayGoalId(todayGoal.getTodayGoalId())
        		.today(todayGoal.getToday())
        		.goalKm(todayGoal.getGoalKm())
                .build();
    }
}
