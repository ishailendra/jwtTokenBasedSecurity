package com.shail.record.exceptionHandler;

public class InvalidJwtTokenException extends Exception {

	private static final long serialVersionUID = 6162600854071448640L;

	public InvalidJwtTokenException(String msg) {
		super(msg);
	}

}
