package com.fourback.runus.domains.running.repository;

import com.fourback.runus.domains.running.domain.TodayGoal;
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
 */
@Repository
public interface TodayGoalRepository extends JpaRepository<TodayGoal, Long> {

    @Query(value = "SELECT today FROM (SELECT today FROM today_goal WHERE user_id = :userId ORDER BY today DESC) WHERE ROWNUM = 1", nativeQuery = true)
    Date findTopTodayByUserId(@Param("userId") Long userId);
}

