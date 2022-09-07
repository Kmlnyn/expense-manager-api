package com.luv2code.expensetrackerapi.service;

import com.luv2code.expensetrackerapi.entity.User;
import com.luv2code.expensetrackerapi.entity.UserModel;

public interface UserService {

	User createUser(UserModel user);
	
	User read();
	
	User updateUser(UserModel user);
	
	void deleteUserById();
	
	User getLoggedInUser();
}
