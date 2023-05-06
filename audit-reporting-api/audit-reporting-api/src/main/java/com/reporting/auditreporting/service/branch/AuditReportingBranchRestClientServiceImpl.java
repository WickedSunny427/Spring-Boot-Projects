package com.reporting.auditreporting.service.branch;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

import com.reporting.auditreporting.model.BranchDTO;

@EnableRetry
@Service
public class AuditReportingBranchRestClientServiceImpl implements IAuditBranchReportingRestClientService {

	@Autowired
	RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingBranchRestClientServiceImpl.class);

	@Value("${branch.url}")
	private String branchUrl;

	@Recover
	public List<BranchDTO> recover(Exception ex) {
		System.out.println("##########Recover##########");
		return null;
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<BranchDTO> findAll() throws URISyntaxException {
		LOGGER.info("Inside AuditReportingBranchRestClientServiceImpl.findAll, and branchGetAllUrl:{}", branchUrl);
		URI uri = new URI(branchUrl);

		ResponseEntity<BranchDTO[]> response = restTemplate.getForEntity(uri, BranchDTO[].class);
		BranchDTO[] branchArray = response.getBody();
		return Arrays.asList(branchArray);
	}

	@Override
	public BranchDTO findByName(String name) throws URISyntaxException, UnsupportedEncodingException {
		LOGGER.info("Inside AuditReportingBranchRestClientServiceImpl.findByName, and branchUrl:{}", branchUrl);
		String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
		String url = String.format("%s/name?name=%s", branchUrl, encodedName);

		URI uri = new URI(url);

		ResponseEntity<BranchDTO> response = restTemplate.getForEntity(uri, BranchDTO.class);
		BranchDTO branchDTO = response.getBody();
		return branchDTO;
	}

}