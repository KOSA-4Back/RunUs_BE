package com.fourback.runus.domains.running.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourback.runus.domains.running.entity.TodayGoal;

public interface RunningRepository extends JpaRepository<TodayGoal, Long> {

}
