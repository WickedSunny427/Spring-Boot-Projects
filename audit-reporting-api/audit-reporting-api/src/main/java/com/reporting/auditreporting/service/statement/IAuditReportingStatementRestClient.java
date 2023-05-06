package com.reporting.auditreporting.service.statement;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import com.reporting.auditreporting.model.TransactionDTO;

public interface IAuditReportingStatementRestClient {
	TransactionDTO findById(int id) throws URISyntaxException;

	List<TransactionDTO> findAll() throws URISyntaxException;

	List<TransactionDTO> findByAccountNumber(int accountNumber) throws URISyntaxException;

	List<TransactionDTO> findByAccountNumberAndDates(int accountNumber, LocalDateTime startDate, LocalDateTime endDate)
			throws URISyntaxException;

	TransactionDTO save(TransactionDTO transactionDTO) throws URISyntaxException;

}
