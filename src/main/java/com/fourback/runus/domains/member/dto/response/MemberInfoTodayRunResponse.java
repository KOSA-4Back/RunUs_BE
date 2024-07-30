package com.fourback.runus.domains.member.dto.response;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.running.domain.TodayGoal;
import java.math.BigDecimal;

import com.fourback.runus.domains.running.dto.response.FindTodayGoalResponse;
import lombok.Builder;

/**
 * packageName    : com.fourback.runus.domains.member.dto.response
 * fileName       : MemberInfoTodayRunResponse
 * author         : 김은정
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        김은정            최초 생성
 */
@Builder
public record MemberInfoTodayRunResponse(
    // members, 오늘 목표, 오늘 달리기 정보 조회 (sum)
    FindMembersResponse findMembersResponse,
    FindTodayGoalResponse findTodayGoalResponse,
    TodayTotalDistanceCalDto todayTotalDistanceCalDto
) {
    public static MemberInfoTodayRunResponse of(FindMembersResponse findMembersResponse,
                                                FindTodayGoalResponse findTodayGoalResponse,
                                                TodayTotalDistanceCalDto todayTotalDistanceCalDto) {
        return MemberInfoTodayRunResponse.builder()
            .findMembersResponse(findMembersResponse)
            .findTodayGoalResponse(findTodayGoalResponse)
            .todayTotalDistanceCalDto(todayTotalDistanceCalDto)
            .build();
    }
}
