package com.fourback.runus.domains.running.repository;

import com.fourback.runus.domains.running.domain.TodayGoal;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


/**
 * packageName    : com.fourback.runus.domains.running.repository
 * fileName       : TodayGoalRepository
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            명칭 수정
 * 2024-07-30        김은정            조회 (오늘 날짜 기준) 생성
 */
@Repository
public interface TodayGoalRepository extends JpaRepository<TodayGoal, Long> {

    @Query(value = "SELECT today FROM (SELECT today FROM today_goal WHERE user_id = :userId ORDER BY today DESC) WHERE ROWNUM = 1", nativeQuery = true)
    Date findTopTodayByUserId(@Param("userId") Long userId);


    // 조회 (오늘 날짜 기준)
    TodayGoal findByUserIdAndToday(@Param("userId") Long userId, @Param("today") LocalDate today);
}

