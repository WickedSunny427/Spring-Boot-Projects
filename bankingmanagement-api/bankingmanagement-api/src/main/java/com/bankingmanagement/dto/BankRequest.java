package com.bankingmanagement.dto;

import java.util.Set;

import lombok.Data;

@Data
public class BankRequest {

	int bankCode;

	String bankName;

	String bankAddress;

	Set<BranchDTO> branches;
}