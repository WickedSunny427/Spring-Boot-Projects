package com.bankingmanagement.service;

import java.util.List;

import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.dto.LoanRequest;
import com.bankingmanagement.exception.LoanNotFoundException;

public interface ILoanService {
	List<LoanDTO> findAll() throws LoanNotFoundException;

	LoanDTO findByType(String type) throws LoanNotFoundException;

	LoanDTO findLoanByTypeCustom(String type) throws LoanNotFoundException;

	LoanDTO findById(int id) throws LoanNotFoundException;

	LoanDTO save(LoanRequest bankRequest) throws LoanNotFoundException;

	String delete(int id) throws LoanNotFoundException;

}