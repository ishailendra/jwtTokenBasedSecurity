package com.shail.record.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.shail.record.util.AppConstant;

@Component
public class AuthenticationFailureHandler  extends SimpleUrlAuthenticationFailureHandler{
	
	private Logger logger = LogManager.getLogger(AuthenticationFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		logger.info("AuthenticationFailureHandler");
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.getWriter().print(AppConstant.INVALD_CREDENTIALS.getMessage());
		response.getWriter().close();
		return;
	}
}
