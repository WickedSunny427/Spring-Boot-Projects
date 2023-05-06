package com.bankingmanagement.exception;

public class TransactionNotFoundException extends Exception {

	private static final long serialVersionUID = -9007801318006057963L;

	public TransactionNotFoundException() {
		super();
	}

	public TransactionNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TransactionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransactionNotFoundException(String message) {
		super(message);
	}

	public TransactionNotFoundException(Throwable cause) {
		super(cause);
	}
}