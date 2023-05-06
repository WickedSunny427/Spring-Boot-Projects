package com.reporting.controller;

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

import com.reporting.model.ApplicationRequest;
import com.reporting.model.ApplicationVO;
import com.reporting.service.IReportingRestClientService;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportingRestClientController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportingRestClientController.class);

	@Autowired
	IReportingRestClientService reportingRestClientService;

	@GetMapping
	public ResponseEntity<List<ApplicationVO>> getApplications() {
		LOGGER.info("Inside the ReportingRestClientController.getApplications");
		List<ApplicationVO> applicationVOS = null;
		try {
			applicationVOS = reportingRestClientService.findAll();
			LOGGER.info("Application response:{}", applicationVOS);
			if (CollectionUtils.isEmpty(applicationVOS)) {
				LOGGER.info("Application details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getApplications", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ApplicationVO>>(applicationVOS, HttpStatus.OK);
	}

	// Get an application by ID.
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationVO> getApplicationById(@PathVariable("id") int id) {
		LOGGER.info("Inside ReportingRestClientController and calling the getApplicationById method ...");
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingRestClientService.findById(id);
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

	@PostMapping
	public ResponseEntity<ApplicationVO> save(@RequestBody ApplicationRequest applicationRequest) {
		LOGGER.info("Inside ReportingRestClientController.save and applicationVO;{}", applicationRequest);
		if (applicationRequest == null) {
			LOGGER.info("Invalid Application request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingRestClientService.saveNew(applicationRequest);
			if (applicationVO == null) {
				LOGGER.info("Application details are not saved");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while saving application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(applicationVO, HttpStatus.OK);
	}

	// Update an existing Application.
	@PutMapping
	public ResponseEntity<String> update(@RequestBody ApplicationRequest applicationRequest) {
		LOGGER.info("Inside ReportingRestClientController.update and applicationVO;{}", applicationRequest);
		if (applicationRequest == null) {
			LOGGER.info("Invalid Application request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String message = null;
		try {
			message = reportingRestClientService.updateNew(applicationRequest);
			if (message == "") {
				LOGGER.info("Application details are not updated");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while updating the application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/*
	 * Delete an existing application.OPTION 1 using RequestParam and
	 * URI(applicationUrl+"?id="+id);
	 */
//	@DeleteMapping
//	public ResponseEntity<String> delete(@RequestParam("id") int id) {

		/*
		 * Delete an existing application.OPTION 2 using PathVariable and URI uri = new
		 * URI(applicationUrl+"/"+id);//PATH VARIABLE
		 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		LOGGER.info("Input to ReportingRestClientController.delete, id:{}", id);
		String response = null;
		try {
			response = reportingRestClientService.deleteById(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

/*	// Find an Application by name.
	@GetMapping("/{name}")
	public ResponseEntity<ApplicationVO> getApplicationByName(@PathVariable("name") String name) {
		
//	@GetMapping()
//	public ResponseEntity<ApplicationVO> getApplicationByName(@RequestParam("name") String name) {
		LOGGER.info("Inside the ReportingRestClientController.getApplicationByName");
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingRestClientService.findByName(name);

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
*/
}