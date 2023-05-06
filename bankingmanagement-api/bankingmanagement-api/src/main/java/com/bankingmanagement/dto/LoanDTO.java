package com.bankingmanagement.dto;

import lombok.Data;

@Data
public class LoanDTO  {

	int loanId;

	String loanType;

	double loanAmount;

	private BranchDTO branchDTO;

	CustomerDTO customer;
}