package com.reporting.auditreporting.controller.branch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.auditreporting.model.BranchDTO;
import com.reporting.auditreporting.service.branch.IAuditBranchReportingRestClientService;

@RestController
@RequestMapping("/api/v1/branches")
public class AuditReportingRestClientBranchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingRestClientBranchController.class);

	@Autowired
	IAuditBranchReportingRestClientService auditReportingBranchRestClientService;

	@GetMapping
	public ResponseEntity<List<BranchDTO>> getBranches() {
		LOGGER.info("Inside the AuditReportingRestClientBranchController.getBranches");
		List<BranchDTO> branchDTOs = null;
		try {
			branchDTOs = auditReportingBranchRestClientService.findAll();
			LOGGER.info("Branch response:{}", branchDTOs);
			if (CollectionUtils.isEmpty(branchDTOs)) {
				LOGGER.info("Branch details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getBranches", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<BranchDTO>>(branchDTOs, HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<BranchDTO> getBranchByName(@PathVariable String name) {
		LOGGER.info("Inside AuditReportingRestClientBranchController.getBranchByName, name:{}", name);
		BranchDTO branchDTO;
		try {
			branchDTO = auditReportingBranchRestClientService.findByName(name);
			if (branchDTO == null) {
				LOGGER.info("Branch details are not found for name:{}", name);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getBranchByName", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BranchDTO>(branchDTO, HttpStatus.OK);
	}

}
