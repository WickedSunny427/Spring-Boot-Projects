package com.reporting.auditreporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuditReportingApiApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AuditReportingApiApplication.class, args);
		LOGGER.info("AUDIT-REPORTING-API IS RUNNING NOW !!!");

	}

}
