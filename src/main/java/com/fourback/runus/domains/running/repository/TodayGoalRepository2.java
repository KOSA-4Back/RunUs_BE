package com.fourback.runus.domains.running.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fourback.runus.domains.running.domain.TodayGoal;


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
public interface TodayGoalRepository2 extends JpaRepository<TodayGoal, Long> {


	 // 조회 (오늘 날짜 기준, 가장 최신의 레코드 하나만 반환)
	TodayGoal findTopByUserIdAndTodayOrderByRegistedAtDesc(@Param("userId") Long userId, @Param("today") LocalDate today);


}
