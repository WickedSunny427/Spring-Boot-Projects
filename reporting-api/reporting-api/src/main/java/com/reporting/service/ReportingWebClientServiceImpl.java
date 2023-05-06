package com.reporting.service;

import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.reporting.model.ApplicationRequest;
import com.reporting.model.ApplicationVO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableRetry
@Service
public class ReportingWebClientServiceImpl implements IReportingWebClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportingWebClientServiceImpl.class);

	@Value("${application.url}")
	private String applicationUrl;

	@Recover
	public List<ApplicationVO> recover(Exception ex) {
		System.out.println("##########Recover##########" + "GET OPERATION !!!");
		return null;
	}

	@Recover
	public ApplicationVO recoverPost(Exception ex) {
		System.out.println("##########Recover##########" + "POST OPERATION !!!");
		return null;
	}

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 6000))
	public List<ApplicationVO> findAll() {
		LOGGER.info("Inside ReportingWebClientServiceImpl.findAll, and applicationGetAllUrl:{}", applicationUrl);
		List<ApplicationVO> response = null;
		try {
			WebClient webClient = WebClient.create(applicationUrl);
			Flux<ApplicationVO> result = webClient.get().retrieve().bodyToFlux(ApplicationVO.class);

			response = result.collectList().block();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ApplicationVO save(ApplicationRequest request) throws URISyntaxException {
		LOGGER.info("Inside ReportingWebClientServiceImpl.save, request:{}", request);
		WebClient webClient = WebClient.create(applicationUrl);

		Mono<ApplicationVO> response = webClient.post().body(Mono.just(request), ApplicationRequest.class).retrieve()
				.bodyToMono(ApplicationVO.class);
		return response.block();
	}

	@Override
	public ApplicationVO update(ApplicationRequest request) throws URISyntaxException {
		LOGGER.info("Inside ReportingWebClientServiceImpl.update, request:{}", request);
		WebClient webClient = WebClient.create(applicationUrl);

		Mono<ApplicationVO> response = webClient.put().body(Mono.just(request), ApplicationRequest.class).retrieve()
				.bodyToMono(ApplicationVO.class);
		return response.block();
	}

	@Override
	public ApplicationVO findById(int id) throws URISyntaxException {
		LOGGER.info("Inside ReportingServiceImpl.findAll, and applicationGetAllUrl:{}", applicationUrl);

		WebClient webClient = WebClient.create(applicationUrl + "/" + id);
		Mono<ApplicationVO> result = webClient.get().retrieve().bodyToMono(ApplicationVO.class);
		return result.block();
	}

	@Override
	public ApplicationVO findByName(String name) throws URISyntaxException {
		LOGGER.info("Inside ReportingServiceImpl.findByName, and applicationGetAllUrl:{}", applicationUrl);

		WebClient webClient = WebClient.create(applicationUrl + "/" + name);
		LOGGER.info("XXXXX" + applicationUrl + "/" + name);
		Mono<ApplicationVO> result = webClient.get().retrieve().bodyToMono(ApplicationVO.class);
		return result.block();
	}

	@Override
	public String delete(int id) throws URISyntaxException {
		String message;
		LOGGER.info("Inside ReportingServiceImpl.delete, and applicationGetAllUrl:{}", applicationUrl);

		WebClient webClient = WebClient.create(applicationUrl + "?id=" + id);// REQUEST PARAM APPENDED WITH ?id=
		try {
			Mono<Void> result = webClient.delete().retrieve().bodyToMono(Void.class);//

			/*
			 * Your block() call explicitly holds the main thread until the publisher
			 * completes. By the time it's completed, it's executed the map() call,
			 * therefore printing the value. Your subscribe() call on the other hand
			 * asynchronously executes the Mono on a separate scheduler, leaving your main
			 * thread to complete.
			 */

			// result.block();
			result.subscribe();
			LOGGER.info("Value of result:" + result);
			message = "Application Deleted Successfully !!!";

		} catch (Exception e) {
			message = "Unable to delete Appliocation !!!";
			e.printStackTrace();
		}
		return message;
	}
}