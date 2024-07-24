     package com.fourback.runus.domains.running.dto.request;

import com.fourback.runus.domains.running.entity.TodayGoal;
import lombok.Builder;

@Builder
public class StartRunningRequest {
    
    private long userId;
    private long goalKm;

    public TodayGoal toEntity() {
        return TodayGoal.builder()  
                        .userId(userId)
                        .goalKm(goalKm)
                        .build();  
    }
}
