package com.fourback.runus.domains.running.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "Today_Goal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodayGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long todayGoalId;
    private long userId;
    private LocalDate today;
    private long goalKm;
    private LocalDateTime registedAt;
    private LocalDateTime deletedAt;

    @Builder
    public TodayGoal(long userId, long goalKm) {
        this.userId = userId;
        this.goalKm = goalKm;
        this.today = LocalDate.now();
        this.registedAt = LocalDateTime.now();
    }
    
    public void changeGoalKm(long goalKm) {
    	this.goalKm = goalKm;
    }
}
