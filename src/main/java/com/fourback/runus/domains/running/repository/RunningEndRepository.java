package com.fourback.runus.domains.running.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourback.runus.domains.running.entity.RunTotalInfos;

public interface RunningEndRepository extends JpaRepository<RunTotalInfos, Long> {

}
