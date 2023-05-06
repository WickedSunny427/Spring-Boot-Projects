package com.bankingmanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.bankingmanagement.dto.TransactionDTO;
import com.bankingmanagement.exception.AccountNotFoundException;
import com.bankingmanagement.exception.TransactionNotFoundException;
import com.bankingmanagement.service.ITransactionService;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private ITransactionService transactionService;

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDTO> findById(@PathVariable int id) {
		try {
			logger.info("Finding transaction with id: {}", id);
			TransactionDTO transactionDTO = transactionService.findById(id);
			return new ResponseEntity<TransactionDTO>(transactionDTO, HttpStatus.OK);
		} catch (TransactionNotFoundException e) {
			logger.error("Transaction with id {} not found", id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<TransactionDTO>> findAll() {
		try {
			logger.info("Finding all transactions");
			List<TransactionDTO> transactionDTOList = transactionService.findAll();
			return new ResponseEntity<List<TransactionDTO>>(transactionDTOList, HttpStatus.OK);
		} catch (TransactionNotFoundException e) {
			logger.error("No transactions found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/account/{accountNumber}")
	public ResponseEntity<List<TransactionDTO>> findByAccountNumber(@PathVariable int accountNumber) {
		try {
			logger.info("Finding transactions for account number: {}", accountNumber);
			List<TransactionDTO> transactionDTOList = transactionService.findByAccountNumber(accountNumber);
			return new ResponseEntity<List<TransactionDTO>>(transactionDTOList, HttpStatus.OK);
		} catch (TransactionNotFoundException | AccountNotFoundException e) {
			logger.error("No transactions found for account number: {}", accountNumber);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/account/{accountNumber}/{startDate}/{endDate}")
	public ResponseEntity<List<TransactionDTO>> findByAccountNumberAndDates(@PathVariable int accountNumber,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		try {
			logger.info("Finding transactions for account number: {} between start date: {} and end date: {}",
					accountNumber, startDate, endDate);
			List<TransactionDTO> transactionDTOList = transactionService.findByAccountNumberAndDates(accountNumber,
					startDate, endDate);
			return new ResponseEntity<List<TransactionDTO>>(transactionDTOList, HttpStatus.OK);
		} catch (TransactionNotFoundException | AccountNotFoundException e) {
			logger.error("No transactions found for account number: {} between start date: {} and end date: {}",
					accountNumber, startDate, endDate);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<TransactionDTO> save(@RequestBody TransactionDTO transactionDTO) {
		try {
			logger.info("Saving transaction with details: {}", transactionDTO);
			TransactionDTO savedTransactionDTO = transactionService.save(transactionDTO);
			return new ResponseEntity<TransactionDTO>(savedTransactionDTO, HttpStatus.CREATED);
		} catch (AccountNotFoundException e) {
			logger.error("Account with account number: {} not found", transactionDTO.getAccountNumber());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}