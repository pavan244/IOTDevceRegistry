package com.iotdevices.registry.tokenValidation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.iotdevices.registry.config.IotEntityConfig;

@Service
@Component
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private IotEntityConfig iotconfig;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return new User(username,"",new ArrayList());
	}

}
