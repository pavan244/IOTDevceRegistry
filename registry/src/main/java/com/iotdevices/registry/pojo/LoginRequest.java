package com.iotdevices.registry.pojo;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
private String username;
private String password;

@JsonCreator
public LoginRequest(@JsonProperty("username") String username,@JsonProperty("password") String password)
		{
	    this.setPassword(password);
	    this.setUserName(username);
		}


public String getUserName() {
	return username;
}
public void setUserName(String userName) {
	this.username = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}	
}
