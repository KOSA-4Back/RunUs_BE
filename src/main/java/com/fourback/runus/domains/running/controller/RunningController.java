package com.fourback.runus.domains.running.controller;

import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import com.fourback.runus.domains.running.dto.request.RecodeRunningRequest;
import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.dto.request.UpdateRunningRequest;
import com.fourback.runus.domains.running.dto.response.EndRunningResponse;
import com.fourback.runus.domains.running.dto.response.StartRunningResponse;
import com.fourback.runus.domains.running.dto.response.TodayGoalResponse;
import com.fourback.runus.domains.running.service.RunningService;
import com.fourback.runus.global.error.errorCode.ResponseCode;

import java.time.LocalDate;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Local;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * packageName    : com.fourback.runus.domains.running.controller
 * fileName       : RunningController
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            전체 메서드 로직 수정
 * 2024-07-31        강희원            strat 로직 수정
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/runnings")
public class RunningController {

    private final RunningService runningService;

    //하루 기준

    //최신 목표 정보 (최신 날짜와 오늘 날짜<프론트> 비교) 1.다를 때 start로 2.같을 때 update V
    @GetMapping("/select/{user-id}")
    public ResponseEntity<TodayGoalResponse> selectRunning(@PathVariable(value = "user-id") Long userId) {
        log.info("userId {}", userId);
        TodayGoalResponse response = runningService.select(userId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //러닝 목표 설정 = > 인공키 생성  V
    @PostMapping("/start")
    public ResponseEntity<ResponseCode> startRunning(@RequestBody StartRunningRequest request) {
        StartRunningResponse response = runningService.save(request);
        log.info("====>>>>>>>>>> goal id, total_info_id : {}, {}", response.TodayGoalId(),
            response.RunTotalInfoId());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseCode.RUN_START.withData(response));
    }

    //러닝 목표 업데이트   V
//    @PutMapping("/update")
//    public ResponseEntity<List<Long>> goalUpdate(@RequestBody UpdateRunningRequest request) {
//        //업데이트 + 정보 불러오기
//        log.info("today : {}", ResponseEntity.status(HttpStatus.CREATED).body(runningService.updateTodayGoal(request)));
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(runningService.updateTodayGoal(request));
//    }

    // 러닝 또 시작 시
    @PutMapping("/update")
    public ResponseEntity<ResponseCode> goalUpdate(@RequestBody UpdateRunningRequest request) {

        StartRunningResponse response = runningService.updateTodayGoal(request);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseCode.RUN_START.withData(response));
    }


    //실시간 러닝 데이터 기록 V
    @PostMapping("/recode")
    public ResponseEntity<String> recodeRunning(@RequestBody RecodeRunningRequest request) {
        log.info("Received request: {}", request);
        runningService.recodeRunning(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Running recoding successfully");
    }


    //러닝 종료 => 프론트에서 받은 인공키로 정보 저장
    @PostMapping("/end")
    public ResponseEntity<ResponseCode> endRunning(@RequestBody EndRunningRequest request) {
    	  System.out.println("Received totalTime: " + request.totalInfoId());
        EndRunningResponse endRunningResponse = runningService.endRunning(request);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseCode.RUN_END.withData(endRunningResponse));
    }

}
