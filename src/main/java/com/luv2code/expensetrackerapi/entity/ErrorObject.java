package com.luv2code.expensetrackerapi.entity;

import java.util.Date;

public class ErrorObject {

	private Integer statusCode;
	
	private String message;
	
	private Date timeStamp;
	

	public ErrorObject() {
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
