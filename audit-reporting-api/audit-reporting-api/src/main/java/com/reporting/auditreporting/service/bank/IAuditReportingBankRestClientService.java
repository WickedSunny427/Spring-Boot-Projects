package com.reporting.auditreporting.service.bank;

import java.net.URISyntaxException;
import java.util.List;

import com.reporting.auditreporting.model.BankDTO;

public interface IAuditReportingBankRestClientService {

	List<BankDTO> findAll() throws URISyntaxException;
	/*
	 * List<ApplicationVO> findAll() throws URISyntaxException;
	 * 
	 * ApplicationVO findById(int id) throws URISyntaxException;
	 * 
	 * ApplicationVO saveNew(ApplicationRequest request) throws URISyntaxException;
	 * 
	 * String updateNew(ApplicationRequest request) throws URISyntaxException;
	 * 
	 * String deleteById(int id) throws URISyntaxException;
	 * 
	 * ApplicationVO findByName(String name) throws URISyntaxException;
	 */
}