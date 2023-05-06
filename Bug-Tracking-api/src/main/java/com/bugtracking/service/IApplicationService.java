package com.bugtracking.service;

import java.util.List;

import com.bugtracking.exception.ApplicationNotFoundException;
import com.bugtracking.model.ApplicationRequest;
import com.bugtracking.model.ApplicationVO;

public interface IApplicationService {

	List<ApplicationVO> findAll();

	ApplicationVO findById(int id) throws ApplicationNotFoundException;

	ApplicationVO save(ApplicationRequest applicationRequest) throws ApplicationNotFoundException;

	String delete(int id) throws ApplicationNotFoundException;

	ApplicationVO findByName(String name) throws ApplicationNotFoundException;

	ApplicationVO findMyApplicationByName (String name) throws ApplicationNotFoundException;

}