package com.bankingmanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bankingmanagement.dto.TransactionDTO;
import com.bankingmanagement.exception.AccountNotFoundException;
import com.bankingmanagement.exception.TransactionNotFoundException;

public interface ITransactionService {

    TransactionDTO findById(int id) throws TransactionNotFoundException;

    List<TransactionDTO> findAll() throws TransactionNotFoundException;

    List<TransactionDTO> findByAccountNumber(int accountNumber) throws TransactionNotFoundException, AccountNotFoundException;

    List<TransactionDTO> findByAccountNumberAndDates(int accountNumber, LocalDateTime startDate, LocalDateTime endDate) throws TransactionNotFoundException, AccountNotFoundException;

    TransactionDTO save(TransactionDTO transactionDTO) throws AccountNotFoundException;

}
