package com.fourback.runus.domains.running.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fourback.runus.domains.running.entity.TodayGoal;
@Repository
public interface RunningRepository extends JpaRepository<TodayGoal, Long> {
	
	@Query(value = "SELECT today FROM (SELECT today FROM today_goal WHERE user_id = :userId ORDER BY today DESC) WHERE ROWNUM = 1", nativeQuery = true)
	   Date findTopTodayByUserId(@Param("userId") Long userId);
	}

