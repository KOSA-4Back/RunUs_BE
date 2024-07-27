package com.fourback.runus.domains.running.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * packageName    : com.fourback.runus.domains.running.domain
 * fileName       : TodayGoal
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            RunTotalInfos 연관관계 맺음/changeGoalKm() 메서드 수정
 */
@Entity
@Table(name = "Today_Goal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodayGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todayGoalId;
    private Long userId;
    private LocalDate today;
    private long goalKm;
    private LocalDateTime registedAt;
    private LocalDateTime deletedAt;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "todayGoalId") // 외래 키를 설정
    private List<RunTotalInfos> runTotalInfos;


    @Builder
    public TodayGoal(Long userId, long goalKm, List<RunTotalInfos> runTotalInfos) {
        this.userId = userId;
        this.goalKm = goalKm;
        this.runTotalInfos = runTotalInfos;
        this.today = LocalDate.now();
        this.registedAt = LocalDateTime.now();
    }

    public TodayGoal changeGoalKm(long goalKm) {
        this.goalKm = goalKm;
        return this;
    }
}
