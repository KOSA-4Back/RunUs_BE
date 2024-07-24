package com.fourback.runus.domains.running.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.repository.RunningRepository;

@Service

public class RunningService {
	
	
	private RunningRepository repository;

	//러닝 목표 설정
	@Transactional
	public void save(StartRunningRequest request) {
		repository.save(request.toEntity());
		
	}

}
