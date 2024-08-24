package com.vendor.service.impl;

import java.util.List;
import java.util.Optional;

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
			
		  user.setIsEnabled(true);
		  String encodedPassword=passwordEncoder.encode(user.getPassword());
		  user.setPassword(encodedPassword);
		 
		
		UserData saveUser= userRepository.save(user);
		return saveUser;
	}

	@Override
	public UserData getUserByEmail(String email) {
 		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserData> getUsers(String role) {
		return  userRepository.findByRole(role);
		
	}

	@Override
	public Boolean updateAccountStatus(Boolean status, Integer id) {
		
		Optional<UserData> userbyId = userRepository.findById(id);
		
		if (userbyId.isPresent()) {
			UserData userData = userbyId.get();
			userData.setIsEnabled(status);
			userRepository.save(userData);
			return true;
		}
		
		
		return false;
		
	}
	
	

}
