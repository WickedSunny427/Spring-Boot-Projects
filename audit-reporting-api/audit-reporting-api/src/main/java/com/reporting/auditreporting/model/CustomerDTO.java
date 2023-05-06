package com.reporting.auditreporting.model;

import java.util.Set;

import lombok.Data;
@Data
public class CustomerDTO {

	int custId;

	String custName;

	long custPhone;

	String custAddress;

	Set<LoanDTO> loansDTOs;

	Set<AccountDTO> accountsDTOs;
}