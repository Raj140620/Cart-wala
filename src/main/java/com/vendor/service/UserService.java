package com.vendor.service;

import com.vendor.model.UserData;

public interface UserService {
	
	public UserData saveUser(UserData user);

	public UserData getUserByEmail(String email);
}
