package com.luv2code.expensetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.expensetrackerapi.entity.AuthModel;
import com.luv2code.expensetrackerapi.entity.JwtToken;
import com.luv2code.expensetrackerapi.entity.User;
import com.luv2code.expensetrackerapi.entity.UserModel;
import com.luv2code.expensetrackerapi.security.CustomUserDetailsService;
import com.luv2code.expensetrackerapi.service.UserService;
import com.luv2code.expensetrackerapi.util.JwtUtil;


@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<JwtToken> login(@RequestBody AuthModel authModel) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username & password", e);
		}

		final UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(authModel.getEmail());

		final String generatedToken = jwtUtil.generateToken(loadUserByUsername);

		return new ResponseEntity<JwtToken>(new JwtToken(generatedToken), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserModel user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}

}
