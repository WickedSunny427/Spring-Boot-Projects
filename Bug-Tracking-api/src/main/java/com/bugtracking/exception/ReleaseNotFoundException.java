package com.bugtracking.exception;

public class ReleaseNotFoundException extends Exception{
 
	private static final long serialVersionUID = 1L;

	public ReleaseNotFoundException() {
        super();
    }

    public ReleaseNotFoundException(String message) {
        super(message);
    }

    public ReleaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReleaseNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReleaseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}