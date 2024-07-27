package com.fourback.runus.domains.running.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * packageName    : com.fourback.runus.domains.running.domain
 * fileName       : Locations
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            id성 long -> Long 으로 수정
 */
@Entity
@Table(name = "locations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    private Long totalInfoId;
    private Long userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal distance;
    private long calories;
    private LocalDateTime recordTime;
    private LocalDateTime deletedAt;

    @Builder
    public Locations(Long totalInfoId, Long userId, BigDecimal latitude, BigDecimal longitude, BigDecimal distance, long calories, LocalDateTime recordTime, LocalDateTime deletedAt) {
        this.totalInfoId = totalInfoId;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.calories = calories;
        this.recordTime = recordTime;
        this.deletedAt = deletedAt;
    }
}
