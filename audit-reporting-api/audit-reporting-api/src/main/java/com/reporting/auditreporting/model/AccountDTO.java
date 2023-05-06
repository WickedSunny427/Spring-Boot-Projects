package com.reporting.auditreporting.model;

import lombok.Data;

@Data
public class AccountDTO {

	int accountNumber;

	String accountType;

	double accountBalance;

	private BranchDTO branchDTO;

	CustomerDTO customer;

}