package com.vendor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendor.model.UserData;
import com.vendor.repository.UserRepository;
import com.vendor.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserData saveUser(UserData user) {
		UserData saveUser= userRepository.save(user);
		return saveUser;
	}

}
