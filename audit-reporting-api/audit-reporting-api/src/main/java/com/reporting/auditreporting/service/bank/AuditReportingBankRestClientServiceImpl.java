package com.reporting.auditreporting.service.bank;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.reporting.auditreporting.model.BankDTO;

@EnableRetry
@Service
public class AuditReportingBankRestClientServiceImpl implements IAuditReportingBankRestClientService {

	@Autowired
	RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingBankRestClientServiceImpl.class);

	@Value("${bank.url}")
	private String bankUrl;

	@Recover
	public List<BankDTO> recover(Exception ex) {
		System.out.println("##########Recover##########");
		return null;
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<BankDTO> findAll() throws URISyntaxException {
		LOGGER.info("Inside AuditReportingBankRestClientServiceImpl.findAll, and bankGetAllUrl:{}", bankUrl);
		URI uri = new URI(bankUrl);

		ResponseEntity<BankDTO[]> response = restTemplate.getForEntity(uri, BankDTO[].class);
		BankDTO[] bankArray = response.getBody();
		return Arrays.asList(bankArray);
	}

}