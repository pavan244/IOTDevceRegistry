package com.iotdevices.registry.config;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.iotdevices.registry.pojo.UserAccount;
import com.iotdevices.registry.pojo.UserInfo;

@Repository
@Component
public class IotEntityConfig {

	@PersistenceContext
    private EntityManager entityManager;
	
	
	@Transactional
	public void insertWithQuery(UserInfo userInfo) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
	    entityManager.createNativeQuery("INSERT INTO Account (email, name) VALUES (?,?)")
	      .setParameter(1, userInfo.getEmail())
	      .setParameter(2, userInfo.getName())
	      .executeUpdate();
	}
	
	
	public String selectEmailQuery(String email) {
		 List  list =  entityManager.createNativeQuery("select name from account where email = ?")
	      .setParameter(1, email).getResultList();
	      if(list.size() > 0)
	      {
	    	  return (String) list.get(0);
	      }
	    return "";     
	}
	
	
}
