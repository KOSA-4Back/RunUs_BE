package com.fourback.runus.domains.running.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record StartRunningResponse(
        Long TodayGoalId,
        Long RunTotalInfoId,
        Date today // 추가된 필드
) {
}
