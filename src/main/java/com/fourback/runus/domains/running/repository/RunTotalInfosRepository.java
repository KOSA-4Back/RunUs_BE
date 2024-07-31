package com.fourback.runus.domains.running.repository;

import com.fourback.runus.domains.running.domain.RunTotalInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * packageName    : com.fourback.runus.domains.running.repository
 * fileName       : RunTotalInfosRepository
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            명칭 수정
 * 2024-07-30        김은정            RunTotalInfos 조회 (todayGoalId 기준)/당일 이전 최근 2건 조회 생성
 */
@Repository
public interface RunTotalInfosRepository extends JpaRepository<RunTotalInfos, Long> {

    @Query("SELECT r.totalDistance From RunTotalInfos r Where r.todayGoalId = :todayGoalId")
    List<Long> findAllBytodayGoalId(@Param("todayGoalId") Long todayGoalId);


    // RunTotalInfos 조회 (todayGoalId 기준)
    @Query("SELECT r FROM RunTotalInfos r WHERE r.todayGoalId = :todayGoalId")
    List<RunTotalInfos> findByTodayGoalId(Long todayGoalId);


    // 당일 이전 최근 2건 조회
    List<RunTotalInfos> findTop2ByUserIdAndStartTimeBeforeOrderByStartTimeDesc(Long userId, LocalDateTime startTime);
}
