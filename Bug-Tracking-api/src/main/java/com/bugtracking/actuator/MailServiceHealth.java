package com.bugtracking.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
@Component
public class MailServiceHealth implements HealthIndicator {
	@Override
	public Health health() {
		if (isMailServiceAvailable()) {
			return Health.up().withDetail("Mail Service", "Mail service is up").build();
		} else {
			return Health.down().withDetail("Mail Service", "Mail service is down").build();
		}
	}

	private boolean isMailServiceAvailable() {
		// write the logic to connect mail microservice
		return true;
	}
}