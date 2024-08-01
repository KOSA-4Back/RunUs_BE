package com.fourback.runus.domains.running.dto.response;

import java.time.LocalDate;

import com.fourback.runus.domains.running.domain.TodayGoal;

import lombok.Builder;

/**
 * packageName    : com.fourback.runus.domains.running.dto.response
 * fileName       : TodayGoalResponse
 * author         : 강희원
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        강희원            최초 생성
 */
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
