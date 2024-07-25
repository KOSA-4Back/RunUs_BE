package com.fourback.runus.domains.running.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fourback.runus.domains.running.entity.RunTotalInfos;
@Repository
public interface RunningEndRepository extends JpaRepository<RunTotalInfos, Long> {
	
	@Query("SELECT r.totalDistance From RunTotalInfos r Where r.todayGoalId = :todayGoalId")
	List<Long> findAllBytodayGoalId(@Param("todayGoalId") long todayGoalId);
}
