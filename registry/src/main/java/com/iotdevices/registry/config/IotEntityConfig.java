package com.iotdevices.registry.config;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.iotdevices.registry.pojo.UserAccount;
import com.iotdevices.registry.pojo.UserInfo;
import com.iotdevices.registry.service.DeviceInfo;
import com.iotdevices.registry.service.DeviceMessageDetails;

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
	
	
	public List<DeviceMessageDetails> getDeviceMessageDetails(String deviceId) {
		String query= "SELECT m.messageid, m.telemetric, m.date, m.source, m.deviceid, m.isvalid,d.location ,"
				+ "d.id,d.status,d.version FROM messages m,deviceinfo d where m.deviceid = d.name and d.id = ?";
		 List<Object[]>  list =  entityManager.createNativeQuery(query)
	      .setParameter(1, deviceId).getResultList();
		 List<DeviceMessageDetails> msgs = new ArrayList();
	      for(Object[] obj:list)
	      {
	    	  DeviceMessageDetails details = new DeviceMessageDetails();
	    	  details.setMessageId((String)obj[0]);
	    	  details.setMessage((String)obj[1]);
	    	  Date d = (Date)obj[2];
	    	  details.setDate(d.toString());
	    	  details.setType((String)obj[3]);
	    	  details.setName((String)obj[4]);
	    	  details.setStatus((String)obj[8]);
	    	  details.setLoc((String)obj[6]);
	    	  details.setId((String)obj[7]);
	    	  details.setVersion((String)obj[9]);
	    	  msgs.add(details);
	      }
	    return msgs;     
	}
	
	
	
	
	
	@Transactional
	public String insertDeviceMock(DeviceInfo deviceInfo)
	{
		try
		{
			String jsonDeviceSettings = new Gson().toJson(deviceInfo.getDeviceSettings() );	
			String jsonDataPoints = new Gson().toJson(deviceInfo.getDataPoints() );	
			
			
			
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		entityManager.createNativeQuery("insert into deviceinfo (id, name, description, date,status,version,location,datapoints,settings,userid) VALUES (?,?,?,?,?,?,?,?,?,?)")
		.setParameter(1, deviceInfo.getId()).setParameter(2, deviceInfo.getName()).setParameter(3, deviceInfo.getDescription())
		.setParameter(4, date).setParameter(5, deviceInfo.getStatus()).setParameter(6, deviceInfo.getVersion()).setParameter(7, deviceInfo.getLocation())
		.setParameter(8,jsonDataPoints).setParameter(9, jsonDeviceSettings).setParameter(10, "pavan424@gmail.com").
		executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "SucessFully inserted";
	}
	
	/*
	public List<DeviceInfo> getAllDevicesMock() {
		 List<String[]>  list =  entityManager.createNativeQuery("select * from deviceinfo")
	      .getResultList();
	    
		 List<DeviceInfo> res = new ArrayList();
		 for(Object arr[]:list)
		 {
			 DeviceInfo deviceinfo = new DeviceInfo((String)arr[0],(String)arr[1],(String)arr[2]);
			 Date date = (Date)arr[3];
			 deviceinfo.setDate(date.toString());
			 res.add(deviceinfo);
		 }
		 
		 
	    return res;     
	}
	
	*/
	
	
}
