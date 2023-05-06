package com.bankingmanagement.exception;

public class BankNotFoundException extends Exception {

	private static final long serialVersionUID = -9007801318006057963L;

	public BankNotFoundException() {
		super();
	}

	public BankNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BankNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankNotFoundException(String message) {
		super(message);
	}

	public BankNotFoundException(Throwable cause) {
		super(cause);
	}
}