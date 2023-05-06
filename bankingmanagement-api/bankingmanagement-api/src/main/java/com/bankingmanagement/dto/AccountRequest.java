package com.bankingmanagement.dto;

import lombok.Data;

@Data
public class AccountRequest {

	int accountNumber;

	String accountType;

	double accountBalance;

	private BranchDTO branchDTO;

	CustomerDTO customer;

}