package com.iotdevices.registry.controller;

import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotdevices.registry.config.IotEntityConfig;
import com.iotdevices.registry.jwt.*;
import com.iotdevices.registry.pojo.LoginResponse;
import com.iotdevices.registry.pojo.UserAccount;
import com.iotdevices.registry.pojo.UserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.security.Principal;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2.Userinfo;
import com.google.api.services.oauth2.model.Userinfoplus;



@RestController
@CrossOrigin("*")
public class JwtAuthenticationController {

	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private IotEntityConfig iotEntityConfig;

	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		//authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

	//	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

	//	final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok("Hello World");
	}


	@PostMapping("/create")
	public ResponseEntity<UserInfo> register(@RequestBody UserAccount userAccount) throws Exception{
		LoginResponse loginResponse = new LoginResponse();
		UserInfo userinfo = validate(userAccount.getToken());
		if(userinfo.getEmail() == null || userinfo.getEmail().isEmpty())
		{
			return new ResponseEntity<UserInfo>(userinfo, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(userinfo.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println("token-->"+token);
		String resp = "";
		String isEmailExists = iotEntityConfig.selectEmailQuery(userinfo.getEmail());
		if(isEmailExists.isEmpty())
		{
			iotEntityConfig.insertWithQuery(userinfo);
		}
		loginResponse.setToken(token);
		loginResponse.setGuid(userinfo.getEmail());
		System.out.println(userinfo.getPicture());
		return new ResponseEntity<UserInfo>(userinfo, HttpStatus.OK);
	}
	
	
	public UserInfo  validate(String accessToken)
	{
		try
		{
		UserInfo userInfo = new UserInfo();
		GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);   
		 Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
		          "Oauth2").build();
		 Userinfoplus userinfoPlus = oauth2.userinfo().get().execute();
		 System.out.println(userinfoPlus.toPrettyString());
		 userInfo.setEmail(userinfoPlus.getEmail());
		 userInfo.setName(userinfoPlus.getName());
		 userInfo.setPicture(userinfoPlus.getPicture());
		 return userInfo;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
	}
	
	
	/*
	private String authenticate(String username, String password) throws Exception {
		String response = "";
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			return response;
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	*/
	
	@RequestMapping(value = "/user")
	   public Principal user(Principal principal) {
	      return principal;
	   }
	
	
	
	
	
}
