package com.bankingmanagement.service;

import java.util.List;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.AccountRequest;
import com.bankingmanagement.exception.AccountNotFoundException;

public interface IAccountService {
	List<AccountDTO> findAll() throws AccountNotFoundException;

	AccountDTO findByAccountType(String accountType) throws AccountNotFoundException;

	AccountDTO findByAccountTypeCustom(String accountType) throws AccountNotFoundException;

	AccountDTO findByAccountNumber(int accountNumber) throws AccountNotFoundException;

	AccountDTO save(AccountRequest bankRequest) throws AccountNotFoundException;

	String delete(int id) throws AccountNotFoundException;

}