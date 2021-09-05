package com.shail.record.security.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shail.record.exceptionHandler.InvalidJwtTokenException;
import com.shail.record.security.helper.TokenHelper;
import com.shail.record.security.service.TokenBasedAuthentication;
import com.shail.record.security.service.UserDetailServiceImpl;
import com.shail.record.util.AppConstant;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	//@Autowired
	//private Environment environment;
	
	private Logger logger = LogManager.getLogger(JwtTokenAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("JwtTokenAuthenticationFilter doFilterInternal");
		String token = tokenHelper.getToken(request);
		if(!Objects.isNull(token)) {
			try {
				String username = tokenHelper.getUsernameFromToken(token);
				if(!Objects.isNull(username) && tokenHelper.isValidToken(token)) {
					UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
					TokenBasedAuthentication tokenBasedAuthentication = new TokenBasedAuthentication(userDetails);
					tokenBasedAuthentication.setToken(token);
					SecurityContextHolder.getContext().setAuthentication(tokenBasedAuthentication);
				}
				else {
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					OutputStream output = response.getOutputStream();
					output.write(generateWriterResponse(HttpStatus.BAD_REQUEST, AppConstant.INVALID_JWT_TOKEN.getMessage()).getBytes());
					output.close();
				}
			}
			catch(UsernameNotFoundException exp) {
				logger.info("JwtTokenAuthenticationFilter UsernameNotFoundException");
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				OutputStream output = response.getOutputStream();
				output.write(generateWriterResponse(HttpStatus.BAD_REQUEST, AppConstant.INVALID_JWT_TOKEN.getMessage()).getBytes());
				output.close();
			}
			catch (InvalidJwtTokenException e) {
				logger.info("JwtTokenAuthenticationFilter InvalidJwtTokenException");
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				OutputStream output = response.getOutputStream();
				output.write(generateWriterResponse(HttpStatus.BAD_REQUEST, AppConstant.INVALID_JWT_TOKEN.getMessage()).getBytes());
				output.close();
			}
			catch (IOException e) {
				logger.info("JwtTokenAuthenticationFilter IOException");
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				OutputStream output = response.getOutputStream();
				output.write(generateWriterResponse(HttpStatus.BAD_REQUEST, AppConstant.UNKNOWN_ERROR_OCCURED.getMessage()).getBytes());
				output.close();
			}
		}
		filterChain.doFilter(request, response);
	}

	private String generateWriterResponse(HttpStatus httpStatus, String message) {
		String resp = "Some Error occured while validating your request. Please try again.";
		return resp;
	}

}
