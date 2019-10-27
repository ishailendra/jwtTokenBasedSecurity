package com.devrauxx.record.security.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.devrauxx.record.security.filter.JwtTokenAuthenticationFilter;
import com.devrauxx.record.security.handler.AuthenticationFailureHandler;
import com.devrauxx.record.security.handler.AuthenticationSuccessHandler;
import com.devrauxx.record.security.handler.JwtAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager jwtAuthenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Bean
	public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
		return new JwtTokenAuthenticationFilter();
	}
	
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	private Logger logger = LogManager.getLogger(SecurityConfig.class);
	//TODO: add the uri's
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		logger.info("SecurityConfig configure");
				
		http
		    .csrf().disable()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(new Http403ForbiddenEntryPoint())
	        .and()
	        .addFilterBefore(jwtTokenAuthenticationFilter(), BasicAuthenticationFilter.class)
	        .formLogin()
			.successHandler(successHandler).failureHandler(failureHandler)
			.and()
			.authorizeRequests()
			.antMatchers("/student/register","/student/login").permitAll()
		    .anyRequest().authenticated();
	}
}
