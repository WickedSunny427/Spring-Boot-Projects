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
import com.reporting.service.IReportingWebClientService;

@RestController
@RequestMapping("/api/v2/reports")
public class ReportingWebClientController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportingWebClientController.class);

	@Autowired
	IReportingWebClientService reportingServiceWebClient;

	@GetMapping
	public ResponseEntity<List<ApplicationVO>> getApplications() {
		LOGGER.info("Inside the ReportingWebClientController.getApplications");
		List<ApplicationVO> applicationVOS = null;
		try {
			applicationVOS = reportingServiceWebClient.findAll();
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

	@PostMapping
	public ResponseEntity<ApplicationVO> save(@RequestBody ApplicationRequest applicationRequest) {
		LOGGER.info("Inside ReportingControllerWebClient.save and applicationVO;{}", applicationRequest);
		if (applicationRequest == null) {
			LOGGER.info("Invalid Application request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingServiceWebClient.save(applicationRequest);
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

	@PutMapping
	public ResponseEntity<ApplicationVO> update(@RequestBody ApplicationRequest applicationRequest) {
		LOGGER.info("Inside ReportingControllerWebClient.update and applicationVO;{}", applicationRequest);
		if (applicationRequest == null) {
			LOGGER.info("Invalid Application request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingServiceWebClient.update(applicationRequest);
			if (applicationVO == null) {
				LOGGER.info("Application details are not updated.");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while updating application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(applicationVO, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationVO> getApplicationById(@PathVariable("id") int id) {
		LOGGER.info("Inside ReportingControllerWebClient and calling the getApplicationById method ...");
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingServiceWebClient.findById(id);
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

	@GetMapping("/name")
	public ResponseEntity<ApplicationVO> getApplicationByName(@RequestParam("name") String name) {
		LOGGER.info("Inside the ReportingControllerWebClient.getApplicationByName");
		ApplicationVO applicationVO = null;
		try {
			applicationVO = reportingServiceWebClient.findByName(name);

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

	// Delete an existing application.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		LOGGER.info("Input to ReportingControllerWebClient.delete, id:{}", id);
		String response = null;
		try {
			response = reportingServiceWebClient.delete(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting application");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}