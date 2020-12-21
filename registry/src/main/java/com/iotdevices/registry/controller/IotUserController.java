package com.iotdevices.registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iotdevices.registry.config.IotEntityConfig;
import com.iotdevices.registry.pojo.*;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class IotUserController {

	@Autowired
	private IotEntityConfig iotEntityConfig;
	
	
	
	
	
	/*
	@PostMapping("/authenticate")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login){
		LoginResponse loginResponse = new LoginResponse();
		String response = iotEntityConfig.login(login.getUserName(), login.getPassword());
		loginResponse.setGuid(response);
		if(response.equals(login.getUserName()))
		{
	    return	new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
		}
		return	new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.UNAUTHORIZED);
	}
	*/
	
	
	
	
	
}
