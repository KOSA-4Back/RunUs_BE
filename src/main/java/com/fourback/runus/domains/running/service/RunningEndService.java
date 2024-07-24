package com.fourback.runus.domains.running.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.repository.RunningEndRepository;
import com.fourback.runus.domains.running.repository.RunningRepository;

@Service

public class RunningEndService {
	
	
	private RunningRepository repository;
	private RunningEndRepository endRepository;

	//러닝 목표 설정
	@Transactional
	public void save(StartRunningRequest request) {
		repository.save(request.toEntity());
		
	}

	public void save(EndRunningRequest request) {
		endRepository.save(request.toEntity());
		
	}

}
