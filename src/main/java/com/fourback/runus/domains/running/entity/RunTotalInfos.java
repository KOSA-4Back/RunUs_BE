package com.fourback.runus.domains.running.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "run_total_infos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunTotalInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long totalInfoId;
    private long todayGoalId;
    private long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long totalDistance;
    private long totalCalories;
    private String totalTime;
    private LocalDateTime deletedAt;

    @Builder
    public RunTotalInfos(long userId, long todayGoalId, LocalDateTime startTime, LocalDateTime endTime, long totalDistance, long totalCalories, String totalTime) {
        this.userId = userId;
        this.todayGoalId = todayGoalId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.totalTime = totalTime;
    }
}
