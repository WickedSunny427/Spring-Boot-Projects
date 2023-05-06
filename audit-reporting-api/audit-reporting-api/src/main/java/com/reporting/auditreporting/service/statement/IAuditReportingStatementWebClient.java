package com.reporting.auditreporting.service.statement;

import java.net.URISyntaxException;
import java.time.LocalDateTime;

import com.reporting.auditreporting.model.TransactionDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAuditReportingStatementWebClient {
    Mono<TransactionDTO> findById(int id) throws URISyntaxException;

    Flux<TransactionDTO> findAll() throws URISyntaxException;

    Flux<TransactionDTO> findByAccountNumber(int accountNumber) throws URISyntaxException;

    Flux<TransactionDTO> findByAccountNumberAndDates(int accountNumber, LocalDateTime startDate, LocalDateTime endDate)
            throws URISyntaxException;

    Mono<TransactionDTO> save(TransactionDTO transactionDTO) throws URISyntaxException;
}
