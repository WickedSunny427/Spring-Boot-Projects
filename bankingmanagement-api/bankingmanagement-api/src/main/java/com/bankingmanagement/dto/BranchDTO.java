package com.bankingmanagement.dto;

import java.util.Set;

import lombok.Data;

@Data
public class BranchDTO {
	int branchId;

	String branchName;

	String branchAddress;

	BankDTO bankDTO;

	Set<AccountDTO> accountsDTOs;

	Set<LoanDTO> loansDTOs;

}