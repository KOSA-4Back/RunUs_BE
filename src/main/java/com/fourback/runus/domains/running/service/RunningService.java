package com.fourback.runus.domains.running.service;

import com.fourback.runus.domains.member.repository.MemberRepository;
import com.fourback.runus.domains.running.domain.RunTotalInfos;
import com.fourback.runus.domains.running.domain.TodayGoal;
import com.fourback.runus.domains.running.dto.request.EndRunningRequest;
import com.fourback.runus.domains.running.dto.request.RecodeRunningRequest;
import com.fourback.runus.domains.running.dto.request.StartRunningRequest;
import com.fourback.runus.domains.running.dto.request.UpdateRunningRequest;
import com.fourback.runus.domains.running.dto.response.EndRunningResponse;
import com.fourback.runus.domains.running.dto.response.StartRunningResponse;
import com.fourback.runus.domains.running.repository.LocationsRepository;
import com.fourback.runus.domains.running.repository.RunTotalInfosRepository;
import com.fourback.runus.domains.running.repository.TodayGoalRepository;
import com.fourback.runus.global.error.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * packageName    : com.fourback.runus.domains.running.service
 * fileName       : RunningService
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            전체 메서드 설계한 로직 수정
 */
@RequiredArgsConstructor
@Service
public class RunningService {

    private final MemberRepository memberRepository;
    private final TodayGoalRepository todayGoalRepository;
    private final RunTotalInfosRepository runTotalInfosRepository;
    private final LocationsRepository locationsRepository;


    //최신 러닝 날짜
    @Transactional(readOnly = true)
    public Date select(Long userId) {

        // 받아온 userId 가 데이터베이스에 있는지 확인해야함
        if (checkedUserId(userId)) {
            throw new NotFoundException("존재하지 않은 유저입니다.");
        }

        return todayGoalRepository.findTopTodayByUserId(userId);
    }

    //러닝 목표 설정
    @Transactional
    public StartRunningResponse save(StartRunningRequest request) {

        // 받아온 userId 가 데이터베이스에 있는지 확인해야함
        if (checkedUserId(request.userId())) {
            throw new NotFoundException("존재하지 않은 유저입니다.");
        }

        // TodayGoal 저장
        TodayGoal goalEntity = todayGoalRepository.save(request.toEntity());


        // runTotalInfos 저장
        RunTotalInfos saveRunTotalInfos = runTotalInfosRepository.save(RunTotalInfos.builder()
                .todayGoalId(goalEntity.getTodayGoalId())
                .userId(request.userId())
                .startTime(goalEntity.getRegistedAt())
                .build());

        return StartRunningResponse.builder()
                .TodayGoalId(goalEntity.getTodayGoalId())
                .RunTotalInfoId(saveRunTotalInfos.getTotalInfoId())
                .build();
    }


    // 러닝 또 시작 시
    @Transactional
    public StartRunningResponse updateTodayGoal(UpdateRunningRequest request) {

        // 받아온 userId 가 데이터베이스에 있는지 확인해야함
        if (checkedUserId(request.userId())) {
            throw new NotFoundException("존재하지 않은 유저입니다.");
        }

        // 오늘 목표 업데이트
        TodayGoal updateTodayGoal = todayGoalRepository.findById(request.todayGoalId())
                .orElseThrow(NotFoundException::new)
                .changeGoalKm(request.goalKm());

        // runTotalInfos 저장
        RunTotalInfos saveRunTotalInfos = runTotalInfosRepository.save(RunTotalInfos.builder()
                .todayGoalId(updateTodayGoal.getTodayGoalId())
                .userId(request.userId())
                .startTime(updateTodayGoal.getRegistedAt())
                .build());

        return StartRunningResponse.builder()
                .TodayGoalId(updateTodayGoal.getTodayGoalId())
                .RunTotalInfoId(saveRunTotalInfos.getTotalInfoId())
                .build();
    }


    // 러닝 실시간 데이터 저장
    public void recodeRunning(RecodeRunningRequest request) {
        locationsRepository.save(request.toEntity());
    }


    //러닝 종료시 저장
    @Transactional
    public EndRunningResponse endRunning(EndRunningRequest request) {
        // 종료 시 러닝 실시간 데이터를 프론트에서 axios 별도로 call 해서
        // 먼저 저장하고 러닝 종료를 해야함

        // 달리기 정보 업데이트
        RunTotalInfos modifyTotalInfos = runTotalInfosRepository.findById(request.totalInfoId())
                .orElseThrow(NotFoundException::new);

        // 수정
        modifyTotalInfos.changeTotal(request);
        runTotalInfosRepository.save(modifyTotalInfos);

        return EndRunningResponse.from(modifyTotalInfos);
    }


    // 유저 정보 확인
    private boolean checkedUserId(Long userId) {
        return !memberRepository.existsById(userId);
    }


    private List<Long> getTotalDistance(Long todayGoalId) {
        return runTotalInfosRepository.findAllBytodayGoalId(todayGoalId);
    }
}
