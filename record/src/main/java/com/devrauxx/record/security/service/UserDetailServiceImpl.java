package com.devrauxx.record.security.service;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devrauxx.record.entity.StudentAuthEntity;
import com.devrauxx.record.repository.StudentAuthRepository;
import com.devrauxx.record.util.AppConstant;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	StudentAuthRepository studentAuthRepository;

	private Logger logger = LogManager.getLogger(UserDetailServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("UserDetailServiceImpl loadByUserName");
		StudentAuthEntity authEntity = studentAuthRepository.findByUserName(username);
		if(Objects.isNull(authEntity)) {
			throw new UsernameNotFoundException(AppConstant.INVALID_USERNAME.getMessage());
		}
		
		List<GrantedAuthority> authList = AuthorityUtils.createAuthorityList(authEntity.getRole());
		UserDetails userDetails = new User(authEntity.getUserName(), authEntity.getPassword(), authList);
				
		return userDetails;
	}

}
