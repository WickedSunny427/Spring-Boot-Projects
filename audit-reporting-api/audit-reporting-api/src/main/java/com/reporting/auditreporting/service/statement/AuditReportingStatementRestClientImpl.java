package com.reporting.auditreporting.service.statement;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.reporting.auditreporting.model.TransactionDTO;

@EnableRetry
@Service
public class AuditReportingStatementRestClientImpl implements IAuditReportingStatementRestClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingStatementRestClientImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${transaction.url}")
	private String transactionUrl;
	
	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public TransactionDTO findById(int id) throws URISyntaxException {
	    LOGGER.info("Inside AuditReportingStatementRestClientImpl.findById, and transactionGetByIdUrl:{} with transaction id:{}", transactionUrl, id);
	    URI uri = new URI(transactionUrl + "/" + id);
	    ResponseEntity<TransactionDTO> response = restTemplate.getForEntity(uri, TransactionDTO.class);
	    LOGGER.info("Response received for AuditReportingStatementRestClientImpl.findById with transaction id:{} is {}", id, response);
	    return response.getBody();
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<TransactionDTO> findAll() throws URISyntaxException {
	    LOGGER.info("Inside AuditReportingStatementRestClientImpl.findAll, and transactionGetAllUrl:{}", transactionUrl);
	    URI uri = new URI(transactionUrl);
	    ResponseEntity<TransactionDTO[]> response = restTemplate.getForEntity(uri, TransactionDTO[].class);
	    return Arrays.asList(response.getBody());
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<TransactionDTO> findByAccountNumber(int accountNumber) throws URISyntaxException {
	    LOGGER.info("Inside AuditReportingStatementRestClientImpl.findByAccountNumber, and transactionGetByAccountNumberUrl:{}, accountNumber:{}", transactionUrl, accountNumber);
	    URI uri = new URI(transactionUrl + "/byAccountNumber/" + accountNumber);
	    ResponseEntity<TransactionDTO[]> response = restTemplate.getForEntity(uri, TransactionDTO[].class);
	    return Arrays.asList(response.getBody());
	}

	@Override
	@Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<TransactionDTO> findByAccountNumberAndDates(int accountNumber, LocalDateTime startDate, LocalDateTime endDate)
	        throws URISyntaxException {
	    LOGGER.info("Inside AuditReportingStatementRestClientImpl.findByAccountNumberAndDates, and transactionGetByAccountNumberAndDatesUrl:{}, accountNumber:{}, startDate:{}, endDate:{}", transactionUrl, accountNumber, startDate, endDate);
	    
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionUrl + "/byAccountNumberAndDates/{accountNumber}")
	            .queryParam("startDate", startDate)
	            .queryParam("endDate", endDate);
	    
	    ResponseEntity<TransactionDTO[]> response = restTemplate.getForEntity(builder.buildAndExpand(accountNumber).toUri(), TransactionDTO[].class);
	    return Arrays.asList(response.getBody());
	}



	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public TransactionDTO save(TransactionDTO transactionDTO) throws URISyntaxException {
	    LOGGER.info("Inside AuditReportingStatementRestClientImpl.save, and transactionSaveUrl:{}", transactionUrl);
	    URI uri = new URI(transactionUrl);
	    ResponseEntity<TransactionDTO> response = restTemplate.postForEntity(uri, transactionDTO, TransactionDTO.class);
	    return response.getBody();
	}

}
