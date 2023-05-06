package com.reporting.auditreporting.controller.statement;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.auditreporting.model.TransactionDTO;
import com.reporting.auditreporting.service.statement.IAuditReportingStatementRestClient;

@RestController
@RequestMapping("/api/v1/statements")
public class AuditReportingStatementControllerRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingStatementControllerRestClient.class);

    @Autowired
    private IAuditReportingStatementRestClient auditReportingStatementRestClient;

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransactionById(@PathVariable int id) throws URISyntaxException {
        LOGGER.info("Inside AuditReportingStatementController.getTransactionById with transaction id:{}", id);
        TransactionDTO transactionDTO = auditReportingStatementRestClient.findById(id);
        LOGGER.info("Response received for AuditReportingStatementController.getTransactionById with transaction id:{} is {}", id, transactionDTO);
        return transactionDTO;
    }

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions() throws URISyntaxException {
        LOGGER.info("Inside AuditReportingStatementController.getAllTransactions");
        List<TransactionDTO> transactionDTOs = auditReportingStatementRestClient.findAll();
        LOGGER.info("Response received for AuditReportingStatementController.getAllTransactions is {}", transactionDTOs);
        return transactionDTOs;
    }

    @GetMapping("/transactions/account/{accountNumber}")
    public List<TransactionDTO> getTransactionsByAccountNumber(@PathVariable int accountNumber) throws URISyntaxException {
        LOGGER.info("Inside AuditReportingStatementController.getTransactionsByAccountNumber with accountNumber:{}", accountNumber);
        List<TransactionDTO> transactionDTOs = auditReportingStatementRestClient.findByAccountNumber(accountNumber);
        LOGGER.info("Response received for AuditReportingStatementController.getTransactionsByAccountNumber with accountNumber:{} is {}", accountNumber, transactionDTOs);
        return transactionDTOs;
    }

    @GetMapping("/account/{accountNumber}/{startDate}/{endDate}")
	  public List<TransactionDTO> getTransactionsByAccountNumberAndDates(
      @PathVariable int accountNumber,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws URISyntaxException {

       LOGGER.info("Inside AuditReportingStatementController.getTransactionsByAccountNumberAndDates with accountNumber:{}, startDate:{}, endDate:{}", accountNumber, startDate, endDate);
        List<TransactionDTO> transactionDTOs = auditReportingStatementRestClient.findByAccountNumberAndDates(accountNumber, startDate, endDate);
        LOGGER.info("Response received for AuditReportingStatementController.getTransactionsByAccountNumberAndDates with accountNumber:{}, startDate:{}, endDate:{} is {}", accountNumber, startDate, endDate, transactionDTOs);
        return transactionDTOs;
    }

    @PostMapping("/transactions")
    public TransactionDTO addTransaction(@RequestBody TransactionDTO transactionDTO) throws URISyntaxException {
        LOGGER.info("Inside AuditReportingStatementController.addTransaction with transactionDTO:{}", transactionDTO);
        TransactionDTO addedTransactionDTO = auditReportingStatementRestClient.save(transactionDTO);
        LOGGER.info("Response received for AuditReportingStatementController.addTransaction with transactionDTO:{} is {}", transactionDTO, addedTransactionDTO);
        return addedTransactionDTO;
    }
}
