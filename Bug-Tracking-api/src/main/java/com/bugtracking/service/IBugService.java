package com.bugtracking.service;

import java.util.List;

import com.bugtracking.exception.BugNotFoundException;
import com.bugtracking.model.BugRequest;
import com.bugtracking.model.BugVO;

public interface IBugService {

	List<BugVO> findAll();

	BugVO findById(int id) throws BugNotFoundException;

	BugVO save(BugRequest bugRequest) throws BugNotFoundException;

	String delete(int id) throws BugNotFoundException;

	BugVO findByStatus(String status) throws BugNotFoundException;

	BugVO findMyBugByStatus(String status) throws BugNotFoundException;
}