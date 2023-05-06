package com.reporting.auditreporting.model;

import java.util.Set;

import lombok.Data;

@Data
public class BranchRequest  {
	
	int branchId;

	String branchName;

	String branchAddress;

	BankDTO bankDTO;

	Set<AccountDTO> accountsDTOs;
	
	Set<LoanDTO> loansDTOs;
	
}