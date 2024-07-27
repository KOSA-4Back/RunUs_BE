package com.fourback.runus.domains.running.dto.request;

/**
 * packageName    : com.fourback.runus.domains.running.dto.request
 * fileName       : EndRunningRequest
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            id 성 long -> Long 수정
 */
public record EndRunningRequest(
        Long totalInfoId,
        Long userId,
        long totalDistance,
        long totalCalories
) {
}
