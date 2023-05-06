package com.bugtracking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracking.model.ApplicationRequest;
import com.bugtracking.model.ApplicationVO;
import com.bugtracking.service.IApplicationService;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("api/v1/applications")

public class ApplicationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private IApplicationService applicationService;

	// Fetching all Applications.

	@GetMapping
	public ResponseEntity<List<ApplicationVO>> getApplications() {
		LOGGER.info("Inside ApplicationController and calling the getApplications method ...");
		List<ApplicationVO> applicationVOS = null;

		try {
			applicationVOS = applicationService.findAll();
			LOGGER.info("Application response :{}", applicationVOS);

			if (CollectionUtils.isEmpty(applicationVOS)) {
				LOGGER.info("No Applications found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error(" Error while fetching Application details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ApplicationVO>>(applicationVOS, HttpStatus.OK);
	}

	// Get an application by ID.
	// http://localhost:9090/api/v1/applications/1
	// http://localhost:9090/api/v1/applications?id=1&name=appname
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationVO> getApplicationById(@PathVariable("id") int id) {
		LOGGER.info("Inside ApplicationController and calling the getApplicationById method ...");
		ApplicationVO applicationVO = null;
		try {
			applicationVO = applicationService.findById(id);
			LOGGER.info("Application response:{}", applicationVO);
			if (applicationVO == null) {
				LOGGER.info("Application details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getApplications", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationVO>(applicationVO, HttpStatus.OK);
	}

	// Create a new application.
	@PostMapping
	public ResponseEntity<ApplicationVO> save(@RequestBody ApplicationRequest applicationRequest) {
		LOGGER.info("Inside ApplicationController.save and applicationVO;{}", applicationRequest);
		if (applicationRequest == null) {
			LOGGER.info("Invalid Application request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ApplicationVO applicationVO = null;
		try {
			applicationVO = applicationService.save(applicationRequest);
			if (applicationVO == null) {
				LOGGER.info("Application details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while saving application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ApplicationVO>(applicationVO, HttpStatus.OK);
	}

	// Update an existing Application.
	@PutMapping
	public ResponseEntity<ApplicationVO> update(@RequestBody ApplicationRequest applicationRequest) {
		LOGGER.info("Inside ApplicationController.update and applicationVO;{}", applicationRequest);
		if (applicationRequest == null) {
			LOGGER.info("Invalid Application request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ApplicationVO applicationVO = null;
		try {
			applicationVO = applicationService.save(applicationRequest);
			if (applicationVO == null) {
				LOGGER.info("Application details are not updated");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while updating the application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ApplicationVO>(applicationVO, HttpStatus.OK);
	}

	// Delete an existing application.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		LOGGER.info("Input to ApplicationController.delete, id:{}", id);
		String response = null;
		try {
			response = applicationService.delete(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	// Find an Application by name.
	@GetMapping("/name")
	public ResponseEntity<ApplicationVO> getApplicationByName(@RequestParam("name") String name) {
		LOGGER.info("Inside the ApplicationController.getApplicationByName");
		ApplicationVO applicationVO = null;
		try {
			//applicationVO = applicationService.findByName(name);
			applicationVO = applicationService.findMyApplicationByName(name);
					
			LOGGER.info("Application response:{}", applicationVO);
			if (applicationVO == null) {
				LOGGER.info("Application details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getApplications", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApplicationVO>(applicationVO, HttpStatus.OK);
	}
}