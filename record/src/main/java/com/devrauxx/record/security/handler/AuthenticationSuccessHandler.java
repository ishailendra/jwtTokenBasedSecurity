package com.devrauxx.record.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.devrauxx.record.security.helper.TokenHelper;
import com.devrauxx.record.util.AppConstant;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Value("${security.auth.expire.in}")
	private long EXPIRE_IN;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	private Logger logger = LogManager.getLogger(AuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		logger.info("AuthenticationSuccessHandler onAuthenticationSuccess");
		clearAuthenticationAttributes(request);
		
		User user = (User)authentication.getPrincipal();
		try {
			String jwtToken = tokenHelper.generateToken(user.getUsername(), user.getPassword(), user.getAuthorities());
			response.setContentType(AppConstant.CONTENT_TYPE_JSON.getMessage());
			response.getWriter().write(jwtToken);
			response.getWriter().close();
		}
		catch(Exception e) {
			response.getWriter().write("Unable to issue token!");
			response.getWriter().close();
		}
	}

}
