package com.fourback.runus.domains.running.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

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
    private LocalDate startTime;
    private LocalDate endTime;
    private long totalDistance;
    private long totalCalories;
    private Timestamp deletedAt;

    @Builder
    public RunTotalInfos(long userId, long todayGoalId, LocalDate startTime, LocalDate endTime, long totalDistance, long totalCalories) {
        this.userId = userId;
        this.todayGoalId = todayGoalId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
    }
}
