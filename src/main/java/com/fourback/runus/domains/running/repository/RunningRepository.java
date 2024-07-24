package com.fourback.runus.domains.running.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fourback.runus.domains.running.entity.TodayGoal;
@Repository
public interface RunningRepository extends JpaRepository<TodayGoal, Long> {

}
