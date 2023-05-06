package com.reporting.auditreporting.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDTO {
	private int transactionId;
	private String transactionType;
	private int accountNumber;
	private double amount;
	private int customerId;
	private LocalDateTime transactionDate;
	private String transactionStatus;
}
