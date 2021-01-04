package com.iotdevices.registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iotdevices.registry.azure.ServiceEnrollmentService;
import com.iotdevices.registry.config.IotEntityConfig;
import com.iotdevices.registry.pojo.*;
import com.iotdevices.registry.service.DeviceInfo;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.IndividualEnrollment;


@CrossOrigin(origins="*")
@RestController
public class IotUserController {

	@Autowired
	private IotEntityConfig iotEntityConfig;
	
	@Autowired
	private ServiceEnrollmentService enrollmentService; 
	
	@PostMapping("/createDevice")
	public String create(@RequestBody DeviceInfo deviceInfo){
		try {
			return enrollmentService.createEnrollment(deviceInfo).toJson();
		} catch (Exception e) {
			// TODO: handle exception
			return "Unable to create service";
		}
		
	}
	
	@PostMapping("/getADevice")
	public String getADevice(@RequestBody String deviceId){
		try {
			return enrollmentService.getIndividualDeviceInfo(deviceId).toJson();
		} catch (Exception e) {
			// TODO: handle exception
			return "Unable to find the device";
		}
		
	}
	
	
	@GetMapping("/getAllDevice")
	public String getAllDevice(){
		try {
			return enrollmentService.getAllIndividualEnrollments().toString();
		} catch (Exception e) {
			// TODO: handle exception
			return "No Devices Found";
		}
		
	}
	
	
	@DeleteMapping("/deleteADevice")
	public String deleteADevice(@RequestBody String deviceId){
		try {
			boolean isDeleted = enrollmentService.deleteAnEnrollment(deviceId);
			if(isDeleted)
			{
				return "SucessFully Deleted";
			}
			return deviceId+" Device not Found";
		} catch (Exception e) {
			// TODO: handle exception
			return "Unable to Delete the Device";
		}
		
	}
	
	
	
	
}
