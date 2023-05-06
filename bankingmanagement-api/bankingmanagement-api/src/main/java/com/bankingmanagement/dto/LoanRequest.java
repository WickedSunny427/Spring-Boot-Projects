package com.bankingmanagement.dto;

import lombok.Data;

@Data
public class LoanRequest  {

	int loanId;

	String loanType;

	double loanAmount;

	private BranchDTO branchDTO;

	CustomerDTO customerDTO;
}