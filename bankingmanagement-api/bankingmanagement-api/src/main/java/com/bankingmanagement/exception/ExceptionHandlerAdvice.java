package com.bankingmanagement.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {


	@ExceptionHandler(BankNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleBankException(BankNotFoundException ex, HttpServletRequest request) {
		log.error("Exception while processing bank details", ex);
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(ex.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleException(Exception ex, HttpServletRequest request) {
		log.error("Exception while processing bank details", ex);
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(ex.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;
	}
}

