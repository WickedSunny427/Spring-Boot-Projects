package com.bankingmanagement.exception;

public class BranchNotFoundException extends Exception {

	private static final long serialVersionUID = -9007801318006057963L;

	public BranchNotFoundException() {
		super();
	}

	public BranchNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BranchNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BranchNotFoundException(String message) {
		super(message);
	}

	public BranchNotFoundException(Throwable cause) {
		super(cause);
	}
}