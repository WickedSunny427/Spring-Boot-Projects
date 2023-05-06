package com.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportingApiApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportingApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReportingApiApplication.class, args);
		LOGGER.info("REPORTING-API IS RUNNING NOW !!!");
	}
}