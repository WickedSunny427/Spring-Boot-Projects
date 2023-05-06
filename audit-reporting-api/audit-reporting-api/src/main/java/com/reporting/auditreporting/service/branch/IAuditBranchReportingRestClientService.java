package com.reporting.auditreporting.service.branch;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import com.reporting.auditreporting.model.BranchDTO;

public interface IAuditBranchReportingRestClientService {

	List<BranchDTO> findAll() throws URISyntaxException;

	BranchDTO findByName(String name) throws URISyntaxException, UnsupportedEncodingException;

	// BranchDTO findBranchByNameCustom(String name) throws BranchNotFoundException;

	// BranchDTO findById(int id) throws URISyntaxException;

	// BranchDTO save(BranchRequest branchRequest) throws BranchNotFoundException;

	// String delete(int id) throws BranchNotFoundException;

	// String deleteByBranchName(String name) throws BranchNotFoundException;

	/*
	 * List<ApplicationVO> findAll() throws URISyntaxException;
	 * 
	 * ApplicationVO findById(int id) throws URISyntaxException;
	 * 
	 * ApplicationVO saveNew(ApplicationRequest request) throws URISyntaxException;
	 * 
	 * String updateNew(ApplicationRequest request) throws URISyntaxException;
	 * 
	 * String deleteById(int id) throws URISyntaxException;
	 * 
	 * ApplicationVO findByName(String name) throws URISyntaxException;
	 */
}