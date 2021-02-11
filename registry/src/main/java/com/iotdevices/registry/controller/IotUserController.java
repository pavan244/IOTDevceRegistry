package com.iotdevices.registry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.iotdevices.registry.azure.SecurityProviderTPMEmulatorMyImpl;
import com.iotdevices.registry.azure.ServiceEnrollmentService;
import com.iotdevices.registry.azure.simulate.SimulatedDevice;
import com.iotdevices.registry.config.IotEntityConfig;
import com.iotdevices.registry.pojo.*;
import com.iotdevices.registry.service.DeviceInfo;
import com.iotdevices.registry.service.DeviceMessageDetails;
import com.iotdevices.registry.util.StringUtil;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClient;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientTransportProtocol;
import com.microsoft.azure.sdk.iot.provisioning.device.internal.exceptions.ProvisioningDeviceClientException;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.IndividualEnrollment;


@CrossOrigin(origins="*")
@RestController
public class IotUserController {

	@Autowired
	private IotEntityConfig iotEntityConfig;
	
	@Autowired
	private ServiceEnrollmentService enrollmentService; 
	
	@Autowired
	  private SecurityProviderTPMEmulatorMyImpl securityProviderImpl;
	
	@Autowired
	private StringUtil stringutil;
	
	@PostMapping("/createDevice")
	public DeviceInfo create(@RequestBody DeviceInfo deviceInfo){
		try {
			
			return enrollmentService.createEnrollment(deviceInfo,securityProviderImpl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	@PostMapping("/getADevice")
	public List<DeviceDetails> getADevice(@RequestBody DeviceId device){
		try {
			return iotEntityConfig.getDeviceDetaislString(device.getDeviceId());
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ArrayList();
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
	
	
	
	@PostMapping("/sendMessageCorrect")
	public String sendMessages(@RequestParam String deviceid){
		try {
			SimulatedDevice simulator = new SimulatedDevice();
			String connStr = iotEntityConfig.getDeviceString(deviceid);
			String datapoints = iotEntityConfig.getDataPointsString(deviceid);
			String message = stringutil.createJsonMsgFromTelemetric(datapoints);
			simulator.run(message,connStr);
			return "sucess fully posted";
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "error";
		}
		
	}
	
	
	@PostMapping("/sendMessageWrong")
	public String sendMessagesWrong(@RequestParam String deviceid){
		try {
			SimulatedDevice simulator = new SimulatedDevice();
			String connStr = iotEntityConfig.getDeviceString(deviceid);
			String datapoints = iotEntityConfig.getDataPointsString(deviceid);
			String message = stringutil.createJsonMsgFromTelemetricWrong(datapoints);
			simulator.run(message,connStr);
			return "sucess fully posted";
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "error";
		}
		
	}
	
	
	
	@PostMapping("/getDeviceMessage")
	public List<DeviceMessageDetails> getDeviceMessage(@RequestBody DeviceId device){
		try {
			return iotEntityConfig.getDeviceMessageDetails(device.getDeviceId());
		
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	
	@PostMapping("/getDataPoints")
	public String getDataPoints(@RequestBody DeviceId device){
		try {
			return iotEntityConfig.getDataPoints(device.getDeviceId());
		
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	
	
	
	@PostMapping("/registerDevice")
	public String register(@RequestParam String deviceid,@RequestParam String connString){
		try {
			 iotEntityConfig.insertWithQueryRegisterDevice(deviceid,connString);
		     return "SucessFully Registered";
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "Registration failed";
		}
		
	}
	
	
	@PutMapping("/updateDeviceDataPoints")
	public String updateDeviceDataPoints(@RequestBody UpdateDataPoins deviceInfo){
		try {
			 return iotEntityConfig.updateDeviceDataPoints(deviceInfo);
		    
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "failed to update";
		}
		
	}
	
	
	
	
	
	@GetMapping("/makeDeviceOffline")
	public String makeDeviceOffline(){
		try {
			SimulatedDevice simulator = new SimulatedDevice();
			simulator.closeMessaging();
			return "Closed sucessfully";
		} catch (Exception e) {
			// TODO: handle exception
			return "Unable to Close";
		}
		
	}
	
	
	
	@DeleteMapping("/deleteADevice")
	public String deleteADevice(@RequestBody DeviceId device){
		try {
			boolean isDeleted = enrollmentService.deleteAnEnrollment(device.getDeviceId());
			if(isDeleted)
			{
				return "SucessFully Deleted";
			}
			return device.getDeviceId()+" Device not Found";
		} catch (Exception e) {
			// TODO: handle exception
			return "Unable to Delete the Device";
		}
		
	}
	
	
	
	
}
