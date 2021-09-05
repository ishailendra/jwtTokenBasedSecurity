package com.shail.record.security.helper;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.shail.record.exceptionHandler.InvalidJwtTokenException;
import com.shail.record.util.AppConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenHelper {

	@Value("${security.auth.header.param.name}")
	private String AUTH_HEADER_PARAM_NAME;
	
	@Value("${security.auth.header.token.prefix}")
	private String AUTH_HEADER_TOKEN_PREFIX;
	
	@Value("${security.auth.secret.key}")
	private String SIGNING_KEY;
	
	@Value("${security.auth.expire.in}")
	private Long EXPIRE_IN;
	
	@Value("${security.auth.header.token.username}")
	private String AUTH_HEADER_USERNAME;
	
	@Value("${security.auth.header.token.password}")
	private String AUTH_HEADER_PASSWORD;
	
	@Value("${security.auth.header.token.roles}")
	private String AUTH_HEADER_ROLES;
	
	private Logger logger = LogManager.getLogger(TokenHelper.class);
	
	public String getToken(HttpServletRequest request) {
		logger.info("Inside TokenHelper getToken");
		String authToken = request.getHeader(AUTH_HEADER_PARAM_NAME);
		if(Objects.isNull(authToken)) {
			return null;
		}
		return authToken.substring(AUTH_HEADER_TOKEN_PREFIX.length());
	}
	
	public String getUsernameFromToken(String token) throws InvalidJwtTokenException{
		logger.info("Inside token Helper getUsernameFromToken");
		String username = null;
		try {
			final Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY.getBytes()).parseClaimsJws(token).getBody();
			username = String.valueOf(claims.get(AUTH_HEADER_USERNAME));
		}
		catch(Exception e) {
			throw new InvalidJwtTokenException(AppConstant.INVALID_JWT_TOKEN.getMessage());
		}
		return username;
	}
	
	public boolean isValidToken(String token) throws InvalidJwtTokenException{
		boolean isValid = true;
		try {
			logger.info("Inside isValidToken");
			final Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY.getBytes())
					.parseClaimsJws(token).getBody();
			isValid = !(claims.getExpiration().before(new Date()));
		}
		catch(Exception e) {
			throw new InvalidJwtTokenException(AppConstant.INVALID_JWT_TOKEN.getMessage());
		}
		return isValid;
	}
	
	public String generateToken(String username, String password, Collection<GrantedAuthority> authorities) {
		logger.info("Inside Token helper generateToken");
		Claims claims = Jwts.claims();
		String roles = (authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
		claims.put(AUTH_HEADER_USERNAME,username);
		claims.put(AUTH_HEADER_ROLES, roles);
		claims.put(AUTH_HEADER_PASSWORD, password);
		Date expiration = Date.from(Instant.ofEpochMilli(new Date().getTime()+EXPIRE_IN));
		
		String token = Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, SIGNING_KEY.getBytes()).compact();
		return token;
	}
}
