package com.fourback.runus.domains.running.dto.response;

import java.util.Date;

import lombok.Builder;

/**
 * packageName    : com.fourback.runus.domains.running.dto.response
 * fileName       : StartRunningResponse
 * author         : 김은정
 * date           : 2024-07-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-27        김은정            최초 생성
 */
@Builder
public record StartRunningResponse(
        Long TodayGoalId,
        Long RunTotalInfoId,
        Date today // 추가된 필드
) {
}
