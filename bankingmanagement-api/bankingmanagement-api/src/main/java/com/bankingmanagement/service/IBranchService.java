package com.bankingmanagement.service;

import java.util.List;

import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.BranchRequest;
import com.bankingmanagement.exception.BranchNotFoundException;

public interface IBranchService {
	List<BranchDTO> findAll() throws BranchNotFoundException;

	BranchDTO findByName(String name) throws BranchNotFoundException;

	BranchDTO findBranchByNameCustom(String name) throws BranchNotFoundException;

	BranchDTO findById(int id) throws BranchNotFoundException;

	BranchDTO save(BranchRequest branchRequest) throws BranchNotFoundException;

	String delete(int id) throws BranchNotFoundException;

	String deleteByBranchName(String name) throws BranchNotFoundException;

}