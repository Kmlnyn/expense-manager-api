package com.luv2code.expensetrackerapi.service;

import javax.naming.NameNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.expensetrackerapi.entity.User;
import com.luv2code.expensetrackerapi.entity.UserModel;
import com.luv2code.expensetrackerapi.exceptions.ItemAlreadyExistException;
import com.luv2code.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.luv2code.expensetrackerapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder bCryptEncoder;

	@Override
	public User createUser(UserModel user) {
		if(userRepo.existsByEmail(user.getEmail()))
			throw new ItemAlreadyExistException("Email id already eixst: "+user.getEmail());
		
		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bCryptEncoder.encode(newUser.getPassword()));
		return userRepo.save(newUser);
	}

	@Override
	public User read() {
		Long id = getLoggedInUser().getId();
		return userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User with given id doesnot exist "+id));
	}

	@Override
	public User updateUser(UserModel user) {
		User existingUser = read();
		existingUser.setName(user.getName()!=null?user.getName():existingUser.getName());
		existingUser.setEmail(user.getEmail()!=null?user.getEmail():existingUser.getEmail());
		existingUser.setPassword(user.getPassword()!=null?bCryptEncoder.encode(user.getPassword()):existingUser.getPassword());
		existingUser.setAge(user.getAge()!=0l?user.getAge():existingUser.getAge());
		return userRepo.save(existingUser);
	}

	@Override
	public void deleteUserById() {
		User deleteUser = read();
		userRepo.delete(deleteUser);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepo.findByEmail(email).orElseThrow(
				()->new UsernameNotFoundException("user not found for the given email "+email));
	}

}
