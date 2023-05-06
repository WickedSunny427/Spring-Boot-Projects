package com.reporting.service;

import java.net.URISyntaxException;
import java.util.List;

import com.reporting.model.ApplicationRequest;
import com.reporting.model.ApplicationVO;

public interface IReportingRestClientService {
	List<ApplicationVO> findAll() throws URISyntaxException;

	ApplicationVO findById(int id) throws URISyntaxException;

	ApplicationVO saveNew(ApplicationRequest request) throws URISyntaxException;

	String updateNew(ApplicationRequest request) throws URISyntaxException;

	String deleteById(int id) throws URISyntaxException;

	ApplicationVO findByName(String name) throws URISyntaxException;
}