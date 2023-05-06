package com.bugtracking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracking.model.BugRequest;
import com.bugtracking.model.BugVO;
import com.bugtracking.service.IBugService;

@RestController
@RequestMapping("api/v1/bugs")
public class BugController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BugController.class);

	@Autowired
	private IBugService bugService;

	// Fetching all Bugs.
	@GetMapping
	public ResponseEntity<List<BugVO>> getBugs() {
		LOGGER.info("Inside BugController and calling the getBugs method ...");
		List<BugVO> bugVOS = null;

		try {
			bugVOS = bugService.findAll();
			LOGGER.info("Bug response :{}", bugVOS);

			if (CollectionUtils.isEmpty(bugVOS)) {
				LOGGER.info("No Bugs found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error(" Error while fetching Bug details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<BugVO>>(bugVOS, HttpStatus.OK);
	}

	// Get a bug by ID.
	// http://localhost:9090/api/v1/bugs/1
	// http://localhost:9090/api/v1/bugs?id=1&name=bugname
	@GetMapping("/{id}")
	public ResponseEntity<BugVO> getBugById(@PathVariable("id") int id) {
		LOGGER.info("Inside BugController and calling the getBugById method ...");
		BugVO bugVO = null;
		try {
			bugVO = bugService.findById(id);
			LOGGER.info("Bug response:{}", bugVO);
			if (bugVO == null) {
				LOGGER.info("Bug details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getBugs", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BugVO>(bugVO, HttpStatus.OK);
	}

	// Create a new bug.
	@PostMapping
	public ResponseEntity<BugVO> save(@RequestBody BugRequest bugRequest) {
		LOGGER.info("Inside BugController.save and bugVO;{}", bugRequest);
		if (bugRequest == null) {
			LOGGER.info("Invalid Bug request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BugVO bugVO = null;
		try {
			bugVO = bugService.save(bugRequest);
			if (bugVO == null) {
				LOGGER.info("Bug details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while saving bug");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BugVO>(bugVO, HttpStatus.OK);
	}

	// Update an existing Bug..
	@PutMapping
	public ResponseEntity<BugVO> update(@RequestBody BugRequest bugRequest) {
		LOGGER.info("Inside BugController.update and bugVO;{}", bugRequest);
		if (bugRequest == null) {
			LOGGER.info("Invalid Bug request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BugVO bugVO = null;
		try {
			bugVO = bugService.save(bugRequest);
			if (bugVO == null) {
				LOGGER.info("Bug details are not updated");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while updating the bug");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BugVO>(bugVO, HttpStatus.OK);
	}

	// Delete an existing bug.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		LOGGER.info("Input to BugController.delete, id:{}", id);
		String response = null;
		try {
			response = bugService.delete(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting bug");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// Find a Bug by status.
	@GetMapping("/status")
	public ResponseEntity<BugVO> getBugByStatus(@RequestParam("status") String status) {
		LOGGER.info("Inside the BugController.getBugByStatus");
		BugVO bugVO = null;
		try {
			// bugVO = bugService.findByStatus(status);
			bugVO = bugService.findMyBugByStatus(status);// Custom method invoked.

			LOGGER.info("Bug response:{}", bugVO);
			if (bugVO == null) {
				LOGGER.info("Bug details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getBugByStatus", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BugVO>(bugVO, HttpStatus.OK);
	}

}
