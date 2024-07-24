package com.fourback.runus.domains.running.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.service.RunningEndService;
import com.fourback.runus.domains.running.service.RunningService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/runnings")
public class RunningController {

    private final RunningService runningService;
    private final RunningEndService runningEndService;

   //러닝 목표 설정
    @PostMapping("/start")
    public ResponseEntity<String> startRunning(@RequestBody StartRunningRequest request) {
        runningService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Running started successfully"); //if(response.status == data.status)console.log(data.message)
    }
    
    //실시간 러닝 데이터 기록
    
    
    //러닝 종료시 데이터 기록
    @PostMapping("/end")
    public ResponseEntity<String> endRunning(@RequestBody EndRunningRequest request) {
    	runningEndService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Running end successfully"); //if(response.status == data.status)console.log(data.message)
    }
}
