package com.fourback.runus.domains.running.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import com.fourback.runus.domains.running.dto.request.RecodeRunningRequest;
import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.dto.request.UpdateRunningRequest;
import com.fourback.runus.domains.running.entity.TodayGoal;
import com.fourback.runus.domains.running.repository.RunningEndRepository;
import com.fourback.runus.domains.running.repository.RunningRecodingRepository;
import com.fourback.runus.domains.running.repository.RunningRepository;
import com.fourback.runus.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RunningService {
	
	
	private final RunningRepository repository;
	private final RunningEndRepository endRepository;
	private final RunningRecodingRepository recodeRepository;

	//최신 러닝 날짜
	@Transactional(readOnly = true)
	public Date select(long userId) {	
		return repository.findTopTodayByUserId(userId);
	}	
	
	//러닝 목표 설정
	@Transactional
	public long save(StartRunningRequest request) {
		TodayGoal goalEntity = repository.save(request.toEntity()); 
		return goalEntity.getTodayGoalId();
	}
	
	//러닝 종료시 저장
	public void endRunning(EndRunningRequest request) {
		endRepository.save(request.toEntity());
	}

	@Transactional
	public List<Long> updateTodayGoal(UpdateRunningRequest request) {
		repository.findById(request.todayGoalId())
		.orElseThrow(NotFoundException::new)
		.changeGoalKm(request.goalKm());
		
		return getTotalDistance(request.todayGoalId()); //아이디 + 그날짜 모두의 거리 합
	}
	
	private List<Long> getTotalDistance(long todayGoalId) {
		return endRepository.findAllBytodayGoalId(todayGoalId);
	}

	public void recodeRunning(RecodeRunningRequest request) {
		recodeRepository.save(request.toEntity());
		
	}
}
