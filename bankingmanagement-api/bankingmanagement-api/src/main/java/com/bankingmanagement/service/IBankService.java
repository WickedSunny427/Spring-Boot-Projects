package com.bankingmanagement.service;

import java.util.List;

import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BankRequest;
import com.bankingmanagement.exception.BankNotFoundException;

public interface IBankService {
	List<BankDTO> findAll() throws BankNotFoundException;

	BankDTO findByName(String name) throws BankNotFoundException, InterruptedException;

	BankDTO findBankByNameCustom(String name) throws BankNotFoundException, InterruptedException;

	BankDTO findById(int id) throws BankNotFoundException;

	BankDTO save(BankRequest bankRequest) throws BankNotFoundException;

	String delete(int id) throws BankNotFoundException;

	String deleteByBankName(String name) throws BankNotFoundException;

	void clearCacheForFindByNameCustom();

}