package com.bankingmanagement.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
	private String errorMessage;
	private String requestedURI;

}
