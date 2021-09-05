package com.shail.record.security.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 2006097331367236016L;

	private String token;
	
	private UserDetails userDetails;

	public TokenBasedAuthentication(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.userDetails = userDetails;
	}
	
	@Override
	public boolean isAuthenticated() {
		return true;
	}
	
	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}
	
	

}
