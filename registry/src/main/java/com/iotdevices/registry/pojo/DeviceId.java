package com.iotdevices.registry.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceId {


	
	private String deviceId;
	
	@JsonCreator
	public DeviceId(@JsonProperty("deviceId") String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
