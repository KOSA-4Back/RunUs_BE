package com.fourback.runus.domains.running.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import com.fourback.runus.domains.running.dto.request.RecodeRunningRequest;
import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.dto.request.UpdateRunningRequest;
import com.fourback.runus.domains.running.service.RunningService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/runnings")
public class RunningController {

    private final RunningService runningService;
    
    //하루 기준
    
    //최신 목표 정보 (최신 날짜와 오늘 날짜<프론트> 비교) 1.다를 때 start로 2.같을 때 update V
    @GetMapping("/select/{userId}")
    public ResponseEntity<Date> selectRunning(@PathVariable(value="userId") Long userId) {
    	log.info("userId {}", userId);
    	Date today = runningService.select(userId);
    	
//    	todayEntity.getToday();
//    	long todayGoalId = todayEntity.getTodayGoalId(); //날짜 같으면 사용할 목표 아이디 => 업데이트로 아이디, 목표km 보내주기
    	log.info("today : {}", today);
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(today); 
    }

    //러닝 목표 설정 = > 인공키 생성  V
    @PostMapping("/start")
    public ResponseEntity<Long> startRunning(@RequestBody StartRunningRequest request) {
    	long goalId = runningService.save(request);
    	System.out.println("프론트 데이터" + goalId);
        return ResponseEntity.status(HttpStatus.CREATED).body(goalId);
    }
     
    //러닝 목표 업데이트   V
	 @PutMapping("/update")
	 public ResponseEntity<List<Long>> goalUpdate(@RequestBody UpdateRunningRequest request) {		 
	 	  //업데이트 + 정보 불러오기 	 
		 log.info("today : {}", ResponseEntity.status(HttpStatus.CREATED).body(runningService.updateTodayGoal(request)));
	     return ResponseEntity.status(HttpStatus.CREATED).body(runningService.updateTodayGoal(request)); 
	 }
	 
   
    //러닝 종료 => 프론트에서 받은 인공키로 정보 저장
    @PostMapping("/end")
    public ResponseEntity<String> endRunning(@RequestBody EndRunningRequest request) {
    	runningService.endRunning(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Running end successfully"); 
    }
    
    
    //실시간 러닝 데이터 기록 V
    @PostMapping("/recode")
    public ResponseEntity<String> recodeRunning(@RequestBody RecodeRunningRequest request) {
        log.info("Received request: {}", request);
        runningService.recodeRunning(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Running recoding successfully");
    }


}
