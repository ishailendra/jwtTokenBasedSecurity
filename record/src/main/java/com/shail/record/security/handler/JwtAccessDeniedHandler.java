package com.shail.record.security.handler;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.shail.record.util.AppConstant;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{
	
	@Autowired
	private Environment environment;
	
	private Logger logger = LogManager.getLogger(JwtAccessDeniedHandler.class);
	
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException{
		logger.info("JwtAccessDeniedHandler handle");
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		OutputStream output = response.getOutputStream();
		output.write(environment.getProperty(AppConstant.ACCESS_DENIED.getMessage()).getBytes());
		output.close();
	}
}
