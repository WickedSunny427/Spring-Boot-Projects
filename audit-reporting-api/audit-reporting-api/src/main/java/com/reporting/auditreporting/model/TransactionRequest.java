package com.reporting.auditreporting.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionRequest {
	private String transactionType;
	private int accountNumber;
	private int amount;
	private int customerId;
	private LocalDateTime transactionDate;
	private String transactionStatus;
}
