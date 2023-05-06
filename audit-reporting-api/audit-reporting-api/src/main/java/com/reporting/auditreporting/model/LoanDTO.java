package com.reporting.auditreporting.model;

import lombok.Data;

@Data
public class LoanDTO  {

	int loanId;

	String loanType;

	double loanAmount;

	private BranchDTO branchDTO;

	CustomerDTO customer;
}