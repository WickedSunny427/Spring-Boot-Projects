package com.reporting.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.annotation.Backoff;

import com.reporting.model.ApplicationRequest;
import com.reporting.model.ApplicationVO;

@EnableRetry
@Service
public class ReportingRestClientServiceImpl implements IReportingRestClientService {

	@Autowired
	RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportingRestClientServiceImpl.class);

	@Value("${application.url}")
	private String applicationUrl;

	@Recover
	public List<ApplicationVO> recover(Exception ex) {
		System.out.println("##########Recover##########");
		return null;
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<ApplicationVO> findAll() throws URISyntaxException {
		LOGGER.info("Inside ReportingRestClientServiceImpl.findAll, and applicationGetAllUrl:{}", applicationUrl);
		URI uri = new URI(applicationUrl);

		ResponseEntity<ApplicationVO[]> response = restTemplate.getForEntity(uri, ApplicationVO[].class);
		ApplicationVO[] applicationArray = response.getBody();
		return Arrays.asList(applicationArray);
	}

	@Override
	public ApplicationVO saveNew(ApplicationRequest request) throws URISyntaxException {
		LOGGER.info("Inside ReportingRestClientServiceImpl.saveNew, request:{}", request);

		URI uri = new URI(applicationUrl);

		LOGGER.info("Application url to save the application:{}", applicationUrl);
		HttpHeaders header = new HttpHeaders();
		HttpEntity<ApplicationRequest> entity = new HttpEntity<ApplicationRequest>(request, header);

		ResponseEntity<ApplicationVO> response = restTemplate.postForEntity(uri, entity, ApplicationVO.class);
		LOGGER.info("Response from application save, status code :{} and data:{}", response.getStatusCode(),
				response.getBody());
		return response.getBody();
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public ApplicationVO findById(int id) throws URISyntaxException {
		// Appending logic to add id to URL.
		// applicationUrl.concat("/").concat(String.valueOf(id));

		LOGGER.info("Inside ReportingRestClientServiceImpl.findById, and applicationGetAllUrl:{}", applicationUrl);
		URI uri = new URI(applicationUrl.concat("/").concat(String.valueOf(id)));

		ResponseEntity<ApplicationVO> response = restTemplate.getForEntity(uri, ApplicationVO.class);
		ApplicationVO application = response.getBody();
		return application;

	}

	@Override
	public String updateNew(ApplicationRequest request) throws URISyntaxException {

		String message;

		LOGGER.info("Inside ReportingRestClientServiceImpl.updateNew, request:{}", request);

		URI uri = new URI(applicationUrl);

		LOGGER.info("Application url to update the application:{}", applicationUrl);
		HttpHeaders header = new HttpHeaders();
		HttpEntity<ApplicationRequest> entity = new HttpEntity<ApplicationRequest>(request, header);

		try {
			restTemplate.put(uri, entity);
			message = "Application with id : " + request.getId() + " updated Successfully !!!";
			LOGGER.info("Application updated Successfully !!!");
		} catch (RestClientException e) {
			message = "Unable to update Application !!!";
			LOGGER.info("Application update failed. !!!");
			e.printStackTrace();
		}
		LOGGER.info("Response from application update");
		return message;
	}

	@Override
	public String deleteById(int id) throws URISyntaxException {
		// Appending logic to add id to URL.
		// applicationUrl.concat("/").concat(String.valueOf(id));
		String message = null;

		LOGGER.info("Inside ReportingRestClientServiceImpl.deleteById, and applicationGetAllUrl:{}", applicationUrl);

		try {
			LOGGER.info("URL with Appended ID : " + applicationUrl.concat("/").concat(String.valueOf(id)));
			//URI uri = new URI(applicationUrl + "?id=" + id);// REQUEST PARAM
			 URI uri = new URI(applicationUrl+"/"+id);//PATH VARIABLE

			restTemplate.delete(String.valueOf(uri), String.class);
			message = "Application deleted Successfully !!!";
			LOGGER.info("Application deleted Successfully !!!");
		} catch (RestClientException e) {
			message = "Unable to delete Application !!!";
			LOGGER.info("Application delete failed. !!!");
			e.printStackTrace();
		}
		LOGGER.info("Response from application delete");
		return message;
	}

	@Override
	public ApplicationVO findByName(String name) throws URISyntaxException {
		LOGGER.info("Inside ReportingRestClientServiceImpl.findByName, and applicationGetAllUrl:{}", applicationUrl);
		URI uri = new URI(applicationUrl.concat("/").concat(String.valueOf(name)));
		//URI uri = new URI(applicationUrl.concat("/name/").concat(String.valueOf(name)));
		//URI uri = new URI(applicationUrl.concat("?name=").concat(String.valueOf(name)));
		LOGGER.info("URI Value is uri:{}", uri);
		ResponseEntity<ApplicationVO> response = restTemplate.getForEntity(uri, ApplicationVO.class);
		ApplicationVO application = response.getBody();
		return application;
	}
}