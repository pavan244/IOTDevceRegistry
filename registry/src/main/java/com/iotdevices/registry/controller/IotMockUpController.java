package com.iotdevices.registry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iotdevices.registry.config.IotEntityConfig;
import com.iotdevices.registry.service.DeviceInfo;

@CrossOrigin(origins="*")
@RestController
public class IotMockUpController {

	@Autowired
	private IotEntityConfig iotEntityConfig;
	
	@PostMapping("/createDeviceMock")
	public String create(@RequestBody DeviceInfo deviceInfo){
		try {
			return iotEntityConfig.insertDeviceMock(deviceInfo);
		} catch (Exception e) {
			// TODO: handle exception
			return "Unable to create service";
		}
	}
	
	/*
	@GetMapping("/getAllDeviceMock")
	public List<DeviceInfo> getAllDevice(){
		try {
			return  iotEntityConfig.getAllDevicesMock();
		} catch (Exception e) {
			// TODO: handle exception
			return new ArrayList();
		}
		
	}
	*/
	
	
	
}
