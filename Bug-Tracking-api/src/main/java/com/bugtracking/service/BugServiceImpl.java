package com.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtracking.entity.Bug;
import com.bugtracking.exception.BugNotFoundException;
import com.bugtracking.model.BugRequest;
import com.bugtracking.model.BugVO;
import com.bugtracking.repository.BugRepository;

@Service
public class BugServiceImpl implements IBugService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BugServiceImpl.class);

	@Autowired
	BugRepository bugRepository;

	@Override
	public List<BugVO> findAll() {

		LOGGER.info("Inside BugServiceImpl findAll method ...");
		List<Bug> bugs = bugRepository.findAll();
		LOGGER.info("Fetching all bugs response : {}", bugs);

		List<BugVO> bugVOS = bugs.parallelStream().map(bug -> {

			BugVO bugVO = new BugVO();

			bugVO.setId(bug.getId());
			bugVO.setRelease(bug.getRelease());
			bugVO.setStatus(bug.getStatus());
			bugVO.setType(bug.getType());
			bugVO.setApplication(bug.getApplication());
			bugVO.setApplicationImpacted(bug.getApplicationImpacted());
			bugVO.setAssignedTo(bug.getAssignedTo());
			bugVO.setCreatedBy(bug.getCreatedBy());
			bugVO.setCreatedOn(bug.getCreatedOn());
			bugVO.setDescription(bug.getDescription());

			return bugVO;
		}).collect(Collectors.toList());
		return bugVOS;
	}

	@Override
	public BugVO findById(int bugId) throws BugNotFoundException {
		LOGGER.info("Inside BugServiceImpl findById method ...");
		Optional<Bug> bug = bugRepository.findById(bugId);
		LOGGER.info("Fetching an bug response : {}", bug);
		if (!bug.isPresent()) {
			LOGGER.error("No such bug present !!!");
			throw new BugNotFoundException("No such bug present !!!");
		} else {
			BugVO bugVO = new BugVO();

			bugVO.setId(bug.get().getId());
			bugVO.setRelease(bug.get().getRelease());
			bugVO.setStatus(bug.get().getStatus());
			bugVO.setType(bug.get().getType());
			bugVO.setApplication(bug.get().getApplication());
			bugVO.setApplicationImpacted(bug.get().getApplicationImpacted());
			bugVO.setAssignedTo(bug.get().getAssignedTo());
			bugVO.setCreatedBy(bug.get().getCreatedBy());
			bugVO.setCreatedOn(bug.get().getCreatedOn());
			bugVO.setDescription(bug.get().getDescription());

			return bugVO;
		}
	}

	@Override
	public BugVO save(BugRequest bugRequest) throws BugNotFoundException {
		LOGGER.info("Inside the BugServiceImpl.save method and params, bugRequest:{}", bugRequest);

		if (bugRequest == null) {
			LOGGER.info("Invalid bug request");
			throw new BugNotFoundException("Invalid bug request");
		}

		Bug bug = new Bug();
		if (bugRequest.getId() > 0) {
			bug.setId(bugRequest.getId());
		}

		bug.setId(bugRequest.getId());
		bug.setRelease(bugRequest.getRelease());
		bug.setStatus(bugRequest.getStatus());
		bug.setType(bugRequest.getType());
		bug.setApplication(bugRequest.getApplication());
		bug.setApplicationImpacted(bugRequest.getApplicationImpacted());
		bug.setAssignedTo(bugRequest.getAssignedTo());
		bug.setCreatedBy(bugRequest.getCreatedBy());
		bug.setCreatedOn(bugRequest.getCreatedOn());
		bug.setDescription(bugRequest.getDescription());

		Bug bugResponse = bugRepository.save(bug);
		BugVO bugVO = null;
		if (bugResponse != null) {
			LOGGER.info("Bug Response, bugResponse:{}", bugResponse);
			bugVO = new BugVO();

			bugVO.setId(bug.getId());
			bugVO.setRelease(bug.getRelease());
			bugVO.setStatus(bug.getStatus());
			bugVO.setType(bug.getType());
			bugVO.setApplication(bug.getApplication());
			bugVO.setApplicationImpacted(bug.getApplicationImpacted());
			bugVO.setAssignedTo(bug.getAssignedTo());
			bugVO.setCreatedBy(bug.getCreatedBy());
			bugVO.setCreatedOn(bug.getCreatedOn());
			bugVO.setDescription(bug.getDescription());
		}
		return bugVO;
	}

	// Delete an Existing bug.
	@Override
	public String delete(int id) throws BugNotFoundException {
		LOGGER.info("Input to BugServiceImpl.delete, id:{}", id);
		if (id < 0) {
			LOGGER.info("Invalid bug id");
			throw new BugNotFoundException("Invalid bug id");
		}
		try {
			bugRepository.deleteById(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting bug");
			throw new BugNotFoundException("Exception while deleting bug");
		}
		return "Bug has been deleted";
	}

	@Override
	public BugVO findByStatus(String status) throws BugNotFoundException {
		LOGGER.info("Inside BugServiceImpl.findByStatus and status: {}", status);
		BugVO bugVO = null;
		if (status == null) {
			LOGGER.info("Invalid bug status:{}", status);
			throw new BugNotFoundException("Bug status is not valid");
		}
		Optional<Bug> bug = bugRepository.findByStatus(status);
		LOGGER.info("Default findByStatus invoked Successfully !!!");

		if (bug.isPresent()) {
			LOGGER.info("Bug details for the status {} and the values :{}", status, bug.get());

			bugVO = new BugVO();

			bugVO.setId(bug.get().getId());
			bugVO.setRelease(bug.get().getRelease());
			bugVO.setStatus(bug.get().getStatus());
			bugVO.setType(bug.get().getType());
			bugVO.setApplication(bug.get().getApplication());
			bugVO.setApplicationImpacted(bug.get().getApplicationImpacted());
			bugVO.setAssignedTo(bug.get().getAssignedTo());
			bugVO.setCreatedBy(bug.get().getCreatedBy());
			bugVO.setCreatedOn(bug.get().getCreatedOn());
			bugVO.setDescription(bug.get().getDescription());
		}
		return bugVO;
	}

	@Override
	public BugVO findMyBugByStatus(String status) throws BugNotFoundException {
		LOGGER.info("Inside BugServiceImpl.findMyBugByStatus and status: {}", status);
		BugVO bugVO = null;
		if (status == null) {
			LOGGER.info("Invalid bug status:{}", status);
			throw new BugNotFoundException("Bug status is not valid");
		}
		Optional<Bug> bug = bugRepository.findBugByStatusCustom(status);// Custom method called.
		LOGGER.info("Custom findMyBugByStatus invoked Successfully !!!");

		if (bug.isPresent()) {
			LOGGER.info("Bug details for the status {} and the values :{}", status, bug.get());

			bugVO = new BugVO();

			bugVO.setId(bug.get().getId());
			bugVO.setRelease(bug.get().getRelease());
			bugVO.setStatus(bug.get().getStatus());
			bugVO.setType(bug.get().getType());
			bugVO.setApplication(bug.get().getApplication());
			bugVO.setApplicationImpacted(bug.get().getApplicationImpacted());
			bugVO.setAssignedTo(bug.get().getAssignedTo());
			bugVO.setCreatedBy(bug.get().getCreatedBy());
			bugVO.setCreatedOn(bug.get().getCreatedOn());
			bugVO.setDescription(bug.get().getDescription());
		}
		return bugVO;
	}
}