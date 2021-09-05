package com.shail.record.exceptionHandler;

public class StudentException extends Exception{

	private static final long serialVersionUID = 5506368325433509710L;

	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public StudentException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public StudentException() {
		super();
	}
	
	
	
}
