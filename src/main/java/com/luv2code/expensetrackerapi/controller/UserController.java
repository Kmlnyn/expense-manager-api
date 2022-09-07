package com.luv2code.expensetrackerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.expensetrackerapi.entity.User;
import com.luv2code.expensetrackerapi.entity.UserModel;
import com.luv2code.expensetrackerapi.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserById(){
		return new ResponseEntity<User>(userService.read(), HttpStatus.OK);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<User> updateUserById(@RequestBody UserModel user){
		return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
	}
	
	@DeleteMapping("/deactivate")
	public ResponseEntity<User> deleteUserById(){
		userService.deleteUserById();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}
