package com.fourback.runus.domains.running.dto.request;

import com.fourback.runus.domains.running.entity.TodayGoal;
import lombok.Builder;

@Builder
public record StartRunningRequest(
		long userId,
		long goalKm
) {
    public TodayGoal toEntity() {
        return TodayGoal.builder()  
                        .userId(userId)
                        .goalKm(goalKm)
                        .build();  
    }
}
