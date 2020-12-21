package com.iotdevices.registry.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccount {
	
     	private String name;
		
		private String email;
		
		private String token;
		
		


		@JsonCreator
		public UserAccount(@JsonProperty("name") String name,@JsonProperty("email") String email,
				@JsonProperty("token") String token)
				{
			    this.setName(name);
			    this.setEmail(email);
			    this.setToken(token);
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

    public String getToken() {
		return token;
	}


    public void setToken(String token) {
		this.token = token;
	}
		
	
	
}
