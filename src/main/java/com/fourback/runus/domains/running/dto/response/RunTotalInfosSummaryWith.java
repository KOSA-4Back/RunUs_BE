package com.fourback.runus.domains.running.dto.response;

import lombok.Builder;

import java.time.LocalDate;


/**
 * packageName    : com.fourback.runus.domains.running.dto.response
 * fileName       : RunTotalInfosSummaryWith
 * author         : 김은정
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        김은정            최초 생성
 */
@Builder
public record RunTotalInfosSummaryWith(
        LocalDate date,
        long distance,
        long calories
) {
}
