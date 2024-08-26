package com.vendor.service;

import java.util.List;

import com.vendor.model.UserData;

public interface UserService {
	
	public UserData saveUser(UserData user);

	public UserData getUserByEmail(String email);
	
	public List<UserData> getUsers(String role);

	public Boolean updateAccountStatus(Boolean status, Integer id);
	
	public void increaseFailedAttempt(UserData user);
	
	public void userAccountLock(UserData user);
	
	public boolean unlockAccountTimeExpired(UserData user);
	
	public void resetAttempt(int userId); 
}
