package com.bankingmanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bankingmanagement.dto.TransactionDTO;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Transaction;
import com.bankingmanagement.exception.AccountNotFoundException;
import com.bankingmanagement.exception.TransactionNotFoundException;
import com.bankingmanagement.repository.AccountRepository;
import com.bankingmanagement.repository.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public TransactionDTO findById(int id) throws TransactionNotFoundException {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction not found for id: " + id));
		return toDto(transaction);
	}

	@Override
	public List<TransactionDTO> findAll() throws TransactionNotFoundException {
		List<Transaction> transactions = transactionRepository.findAll();
		if (CollectionUtils.isEmpty(transactions)) {
			throw new TransactionNotFoundException("No transactions found");
		}
		return toDto(transactions);
	}

	@Override
	public List<TransactionDTO> findByAccountNumber(int accountNumber)
			throws TransactionNotFoundException, AccountNotFoundException {
		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
				() -> new AccountNotFoundException("Account not found for account number: " + accountNumber));
		List<Transaction> transactions = transactionRepository.findByAccount(account);
		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transactions found for account number: " + accountNumber);
		}
		return toDto(transactions);
	}

	@Override
	public List<TransactionDTO> findByAccountNumberAndDates(int accountNumber, LocalDateTime startDate, LocalDateTime endDate)
			throws TransactionNotFoundException, AccountNotFoundException {
		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
				() -> new AccountNotFoundException("Account not found for account number: " + accountNumber));
		List<Transaction> transactions = transactionRepository.findByAccountAndTransactionDateBetween(account,
				startDate, endDate);
		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transactions found for account number: " + accountNumber
					+ " between dates " + startDate + " and " + endDate);
		}
		return toDto(transactions);
	}

	@Override
	public TransactionDTO save(TransactionDTO transactionDTO) throws AccountNotFoundException {
		Account account = accountRepository.findByAccountNumber(transactionDTO.getAccountNumber())
				.orElseThrow(() -> new AccountNotFoundException(
						"Account not found for account number: " + transactionDTO.getAccountNumber()));

		Transaction transaction = toEntity(transactionDTO, account);
		transaction = transactionRepository.save(transaction);
		return toDto(transaction);
	}

	private TransactionDTO toDto(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setTransactionId(transaction.getTransactionId());
		transactionDTO.setAccountNumber(transaction.getAccount().getAccountNumber());
		transactionDTO.setTransactionType(transaction.getTransactionType());
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setTransactionDate(transaction.getTransactionDate());
		transactionDTO.setCustomerId(transaction.getCustomer().getCustId());
		transactionDTO.setTransactionStatus(transaction.getStatus());
		return transactionDTO;
	}

	private List<TransactionDTO> toDto(List<Transaction> transactions) {
		return transactions.stream().map(this::toDto).toList();
	}

	private Transaction toEntity(TransactionDTO transactionDTO, Account account) {
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(transactionDTO.getTransactionType());
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setTransactionDate(transactionDTO.getTransactionDate());
		transaction.setStatus(transactionDTO.getTransactionStatus());

		transaction.setCustomer(account.getCustomer());
		return transaction;
	}

}
