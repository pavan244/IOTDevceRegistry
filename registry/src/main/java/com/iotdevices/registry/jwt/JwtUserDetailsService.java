package com.iotdevices.registry.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iotdevices.registry.config.IotEntityConfig;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	private IotEntityConfig iotEntityConfig;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	//	String password = iotEntityConfig.selectPasswordQuery(username);
	//	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//password=encoder.encode(password);
		return new User(username, "",
					new ArrayList<>());
		
	}
}
