package com.iotdevices.registry.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceMessage {

	private String deviceId;
	public String getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	private String message;
		


		@JsonCreator
		public DeviceMessage(@JsonProperty("deviceId") String deviceId,@JsonProperty("message") String message)
		{
			this.deviceId = deviceId;
			this.message = message;
		}
	
	
}
