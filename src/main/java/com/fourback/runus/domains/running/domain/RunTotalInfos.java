package com.fourback.runus.domains.running.domain;

import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * packageName    : com.fourback.runus.domains.running.domain
 * fileName       : RunTotalInfos
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            Locations 연관관계 맺음/changeTotal(), calculateWorkTime() 메서드 추간
 * 2024-07-31        강희원            totalTime 수정
 */
@Entity
@Table(name = "run_total_infos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunTotalInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long totalInfoId;
    private Long todayGoalId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long totalDistance;
    private long totalCalories;
    private String totalTime;
    private LocalDateTime deletedAt;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "totalInfoId")
    private List<Locations> locationsList;


    @Builder
    public RunTotalInfos(Long userId, Long todayGoalId, LocalDateTime startTime, LocalDateTime endTime, long totalDistance, long totalCalories, String totalTime,
        List<Locations> locationsList) {
        this.userId = userId;
        this.todayGoalId = todayGoalId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.totalTime = totalTime;
        this.locationsList = locationsList;
    }

    // 업데이트
    public void changeTotal(EndRunningRequest request) {
        this.endTime = LocalDateTime.now();
        this.totalDistance = request.totalDistance();
        this.totalCalories = request.totalCalories();
        this.totalTime = request.totalTime();
    }

}
