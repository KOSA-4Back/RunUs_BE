package com.fourback.runus.domains.running.dto.request;

import lombok.Builder;

@Builder
public record UpdateRunningRequest(
		long todayGoalId,
		long goalKm
) {

}
