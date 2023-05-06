package com.reporting.auditreporting.service.statement;

import java.net.URISyntaxException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.reporting.auditreporting.model.TransactionDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableRetry
@Service
public class AuditReportingStatementWebClientImpl implements IAuditReportingStatementWebClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditReportingStatementWebClientImpl.class);

//    @Autowired
//    private WebClient webClient;

    @Value("${transaction.url}")
    private String transactionUrl;

    @Override
    public Mono<TransactionDTO> findById(int id) throws URISyntaxException {
        LOGGER.info("Sending request to get transaction by id: {}", id);
        WebClient webClient = WebClient.create(transactionUrl);
        return webClient.get()
                .uri("/api/v1/transactions/" + id)
                .retrieve()
                .bodyToMono(TransactionDTO.class)
                .doOnSuccess(response -> LOGGER.info("Received response for transaction by id {}: {}", id, response));
    }

    @Override
    public Flux<TransactionDTO> findAll() throws URISyntaxException {
        LOGGER.info("Sending request to get all transactions");
        WebClient webClient = WebClient.create(transactionUrl);
        return webClient.get()
                .uri("/api/v1/transactions/")
                .retrieve()
                .bodyToFlux(TransactionDTO.class)
                .doOnComplete(() -> LOGGER.info("Received response for all transactions"));
    }

    @Override
    public Flux<TransactionDTO> findByAccountNumber(int accountNumber) throws URISyntaxException {
        LOGGER.info("Sending request to get transactions by account number: {}", accountNumber);
        WebClient webClient = WebClient.create(transactionUrl);
        return webClient.get()
                .uri("/api/v1/transactions/account/" + accountNumber)
                .retrieve()
                .bodyToFlux(TransactionDTO.class)
                .doOnComplete(() -> LOGGER.info("Received response for transactions by account number {}", accountNumber));
    }

    @Override
    public Flux<TransactionDTO> findByAccountNumberAndDates(int accountNumber, LocalDateTime startDate, LocalDateTime endDate) throws URISyntaxException {
        LOGGER.info("Sending request to get transactions by account number {} and dates from {} to {}", accountNumber, startDate, endDate);
        WebClient webClient = WebClient.create(transactionUrl);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/transactions/account/{accountNumber}/{startDate}/{endDate}")
                        .build(accountNumber, startDate, endDate))
                .retrieve()
                .bodyToFlux(TransactionDTO.class)
                .doOnComplete(() -> LOGGER.info("Received response for transactions by account number {} and dates from {} to {}", accountNumber, startDate, endDate));
    }

    @Override
    public Mono<TransactionDTO> save(TransactionDTO transactionDTO) throws URISyntaxException {
        LOGGER.info("Sending request to save transaction: {}", transactionDTO);
        WebClient webClient = WebClient.create(transactionUrl);
        return webClient.post()
                .uri("/api/v1/transactions/")
                .body(Mono.just(transactionDTO), TransactionDTO.class)
                .retrieve()
                .bodyToMono(TransactionDTO.class)
                .doOnSuccess(response -> LOGGER.info("Received response for saved transaction: {}", response));
    }
}
