package com.fourback.runus.domains.member.dto.response;

import java.math.BigDecimal;
import lombok.Builder;

/**
 * packageName    : com.fourback.runus.domains.member.dto.response
 * fileName       : TodayTotalDistanceCalDto
 * author         : 김은정
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        김은정            최초 생성
 */
public record TodayTotalDistanceCalDto(long distance, long calories) {
}
