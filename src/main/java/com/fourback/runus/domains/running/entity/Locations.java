package com.fourback.runus.domains.running.entity;

import java.math.BigDecimal;
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
@Table(name = "locations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;
    private long totalInfoId;
    private long userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal distance;
    private long calories;
    private LocalDateTime recordTime;
    private LocalDateTime deletedAt;

    @Builder
    public Locations(long totalInfoId, long userId, BigDecimal latitude, BigDecimal longitude, BigDecimal distance, long calories, LocalDateTime recordTime, LocalDateTime deletedAt) {
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
