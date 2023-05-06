package com.reporting.service;

import java.net.URISyntaxException;
import java.util.List;

import com.reporting.model.ApplicationRequest;
import com.reporting.model.ApplicationVO;

public interface IReportingWebClientService {
	List<ApplicationVO> findAll() throws URISyntaxException;

	ApplicationVO save(ApplicationRequest request) throws URISyntaxException;

	ApplicationVO update(ApplicationRequest request) throws URISyntaxException;

	ApplicationVO findById(int id) throws URISyntaxException;

	ApplicationVO findByName(String name) throws URISyntaxException;

	String delete(int id) throws URISyntaxException;
}
