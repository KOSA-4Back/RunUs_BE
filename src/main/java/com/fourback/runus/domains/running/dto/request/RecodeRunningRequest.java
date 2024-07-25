package com.fourback.runus.domains.running.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fourback.runus.domains.running.entity.Locations;

import lombok.Builder;


@Builder
public record RecodeRunningRequest( 
		long totalInfoId,
		long userId,      
		BigDecimal latitude,        
		BigDecimal longitude,      
		BigDecimal distance,         
		long calories,         
		LocalDateTime recordTime) {   


     public Locations toEntity() {
         return Locations.builder()
                         .totalInfoId(totalInfoId)
                         .userId(userId)
                         .latitude(latitude)
                         .longitude(longitude)
                         .distance(distance)
                         .calories(calories)
                         .recordTime(LocalDateTime.now())
                         .build();  
     }
}