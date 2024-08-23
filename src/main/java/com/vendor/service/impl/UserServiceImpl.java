package com.vendor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vendor.model.UserData;
import com.vendor.repository.UserRepository;
import com.vendor.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserData saveUser(UserData user) {
		/* user.setRole("ROLE_USER"); */
		
		  String encodedPassword=passwordEncoder.encode(user.getPassword());
		  user.setPassword(encodedPassword);
		 
		
		UserData saveUser= userRepository.save(user);
		return saveUser;
	}

	@Override
	public UserData getUserByEmail(String email) {
 		return userRepository.findByEmail(email);
	}

}
