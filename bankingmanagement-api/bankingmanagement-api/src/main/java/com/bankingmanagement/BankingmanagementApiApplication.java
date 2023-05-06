package com.bankingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@SpringBootApplication
public class BankingmanagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingmanagementApiApplication.class, args);
		log.info(("BANKING MANAGEMENT APPLICATION IS RUNNING NOW !!!"));
	}
}