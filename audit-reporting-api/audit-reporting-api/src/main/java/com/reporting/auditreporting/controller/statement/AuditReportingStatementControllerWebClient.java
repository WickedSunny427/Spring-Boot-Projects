package com.reporting.auditreporting.controller.statement;

import java.net.URISyntaxException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.auditreporting.model.TransactionDTO;
import com.reporting.auditreporting.service.statement.IAuditReportingStatementWebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/statements")
public class AuditReportingStatementControllerWebClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingStatementControllerWebClient.class);

    @Autowired
    private IAuditReportingStatementWebClient webClientService;

    @GetMapping("/transactions/{id}")
       public ResponseEntity<Mono<TransactionDTO>> findById(@PathVariable int id) {
        LOGGER.info("Inside AuditReportingStatementController.findById method with id {}", id);

        try {
            Mono<TransactionDTO> transaction = webClientService.findById(id);
            LOGGER.info("Received response for transaction by id {}: {}", id, transaction.block());
            return ResponseEntity.ok(transaction);
        } catch (URISyntaxException e) {
            LOGGER.error("Error occurred while finding transaction by id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Flux<TransactionDTO>> findAll() {
        LOGGER.info("Inside AuditReportingStatementController.findAll method");

        try {
            Flux<TransactionDTO> transactions = webClientService.findAll();
            LOGGER.info("Received response for all transactions");
            return ResponseEntity.ok(transactions);
        } catch (URISyntaxException e) {
            LOGGER.error("Error occurred while finding all transactions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Flux<TransactionDTO>> findByAccountNumber(@PathVariable int accountNumber) {
        LOGGER.info("Inside AuditReportingStatementController.findByAccountNumber method with account number {}", accountNumber);

        try {
            Flux<TransactionDTO> transactions = webClientService.findByAccountNumber(accountNumber);
            LOGGER.info("Received response for transactions by account number {}", accountNumber);
            return ResponseEntity.ok(transactions);
        } catch (URISyntaxException e) {
            LOGGER.error("Error occurred while finding transactions by account number {}", accountNumber, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/account/{accountNumber}/{startDate}/{endDate}")
    public ResponseEntity<Flux<TransactionDTO>> findByAccountNumberAndDates(@PathVariable int accountNumber,
                                                                            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        LOGGER.info("Inside AuditReportingStatementController.findByAccountNumberAndDates method with account number {} and dates from {} to {}",
                accountNumber, startDate, endDate);

        try {
            Flux<TransactionDTO> transactions = webClientService.findByAccountNumberAndDates(accountNumber, startDate, endDate);
            LOGGER.info("Received response for transactions by account number {} and dates from {} to {}", accountNumber, startDate, endDate);
            return ResponseEntity.ok(transactions);
        } catch (URISyntaxException e) {
            LOGGER.error("Error occurred while finding transactions by account number {} and dates from {} to {}", accountNumber, startDate, endDate, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Mono<TransactionDTO>> save(@RequestBody TransactionDTO transactionDTO) {
        LOGGER.info("Inside AuditReportingStatementController.save method with transaction: {}", transactionDTO);

        try {
            Mono<TransactionDTO> savedTransaction = webClientService.save(transactionDTO);
            LOGGER.info("Received response for saved transaction: {}", savedTransaction.block());
            return ResponseEntity.ok(savedTransaction);
        } catch (URISyntaxException e) {
            LOGGER.error("Error occurred while saving transaction: {}", transactionDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
