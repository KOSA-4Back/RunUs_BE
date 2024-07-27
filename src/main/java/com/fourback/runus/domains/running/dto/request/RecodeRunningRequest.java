package com.fourback.runus.domains.running.dto.request;

import com.fourback.runus.domains.running.domain.Locations;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * packageName    : com.fourback.runus.domains.running.dto.request
 * fileName       : RecodeRunningRequest
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            id 성 long -> Long 수정
 */
@Builder
public record RecodeRunningRequest(
        Long totalInfoId,
        Long userId,
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