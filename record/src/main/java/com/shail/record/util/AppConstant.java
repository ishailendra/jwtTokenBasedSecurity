package com.shail.record.util;

public enum AppConstant {

	INVALID_JWT_TOKEN("Invalid Jwt Token"),
	INVALD_CREDENTIALS("Invalid Credentials"),
	ACCESS_DENIED("Acces Denied"), 
	CONTENT_TYPE_JSON("Content Type JSON"), 
	INVALID_USERNAME("INVALID USERNAME"), 
	UNKNOWN_ERROR_OCCURED("UNKNOWN ERROR OCCURED");
	
	private final String message;
	
	AppConstant(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
