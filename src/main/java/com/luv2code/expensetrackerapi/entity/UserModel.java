package com.luv2code.expensetrackerapi.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModel {

	@NotBlank(message = "Please Enter Name")
	private String name;

	@NotNull(message = "Please Enter Email Id")
	@Email(message="Please Enter Valid Email Id")
	private String email;

	@NotNull(message = "Please Enter the Password")
	@Size(min=5, message = "Minm character should be 5")
	private String password;

	private Long age = 0L;
	
	public UserModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	

}
