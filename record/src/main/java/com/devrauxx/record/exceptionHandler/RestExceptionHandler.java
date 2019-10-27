package com.devrauxx.record.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(StudentException.class)
	public ResponseEntity<?> studentExceptionHandler(Exception e){
		e.printStackTrace();
		return new ResponseEntity<String>("Some Error Occured",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e){
		return new ResponseEntity<String>("Something bad happend. try Again",HttpStatus.BAD_REQUEST);
	}
	
}