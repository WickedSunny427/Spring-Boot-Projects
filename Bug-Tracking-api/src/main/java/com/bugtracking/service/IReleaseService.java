package com.bugtracking.service;

import java.util.List;

import com.bugtracking.exception.ReleaseNotFoundException;
import com.bugtracking.model.ReleaseRequest;
import com.bugtracking.model.ReleaseVO;

public interface IReleaseService {

	List<ReleaseVO> findAll();

	ReleaseVO findById(int id) throws ReleaseNotFoundException;

	ReleaseVO save(ReleaseRequest releaseRequest) throws ReleaseNotFoundException;

	String delete(int id) throws ReleaseNotFoundException;

	ReleaseVO findByName(String name) throws ReleaseNotFoundException;

	ReleaseVO findMyReleaseByName(String name) throws ReleaseNotFoundException;

}