package com.fourback.runus.domains.running.dto.request;


import java.time.LocalDateTime;

import com.fourback.runus.domains.running.entity.RunTotalInfos;

import lombok.Builder;

@Builder
public class EndRunningRequest {
    
    private long todayGoalId;
    private long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long totalDistance;
    private long totalCalories;

    public RunTotalInfos toEntity() {
        return RunTotalInfos.builder()  
                        .todayGoalId(todayGoalId)
                        .userId(userId)
                        .startTime(startTime)
                        .endTime(endTime)
                        .totalDistance(totalDistance)
                        .totalCalories(totalCalories)
                        .build();  
    }
}
