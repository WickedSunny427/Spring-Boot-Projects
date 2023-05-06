package com.reporting.auditreporting.controller.bank;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.auditreporting.model.BankDTO;
import com.reporting.auditreporting.service.bank.IAuditReportingBankRestClientService;

@RestController
@RequestMapping("/api/v1/banks")
public class AuditReportingRestClientBankController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingRestClientBankController.class);

	@Autowired
	IAuditReportingBankRestClientService auditReportingRestClientService;

	@GetMapping
	public ResponseEntity<List<BankDTO>> getBanks() {
		LOGGER.info("Inside the AuditReportingRestClientController.getBanks");
		List<BankDTO> bankDTOs = null;
		try {
			bankDTOs = auditReportingRestClientService.findAll();
			LOGGER.info("Bank response:{}", bankDTOs);
			if (CollectionUtils.isEmpty(bankDTOs)) {
				LOGGER.info("Bank details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getBanks", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<BankDTO>>(bankDTOs, HttpStatus.OK);
	}
}