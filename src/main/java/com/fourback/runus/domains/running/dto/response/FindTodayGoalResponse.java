package com.fourback.runus.domains.running.dto.response;

import com.fourback.runus.domains.running.domain.TodayGoal;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.fourback.runus.domains.running.dto.response
 * fileName       : FindTodayGoalResponse
 * author         : 김은정
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        김은정            최초 생성
 */
@Builder
public record FindTodayGoalResponse (
        Long todayGoalId,
        Long userId,
        LocalDate today,
        long goalKm,
        LocalDateTime registedAt,
        LocalDateTime deletedAt
) {
    public static FindTodayGoalResponse from(TodayGoal todayGoal) {
        return FindTodayGoalResponse.builder()
                .todayGoalId(todayGoal.getTodayGoalId())
                .userId(todayGoal.getUserId())
                .today(todayGoal.getToday())
                .goalKm(todayGoal.getGoalKm())
                .registedAt(todayGoal.getRegistedAt())
                .deletedAt(todayGoal.getDeletedAt())
                .build();
    }
}
