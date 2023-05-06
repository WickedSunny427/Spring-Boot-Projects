package com.reporting.auditreporting.model;

import java.util.Set;

import lombok.Data;

@Data
public class BankRequest {

	int bankCode;

	String bankName;

	String bankAddress;

	Set<BranchDTO> branches;
}