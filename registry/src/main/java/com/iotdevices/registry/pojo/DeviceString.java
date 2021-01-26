package com.iotdevices.registry.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceString {

private String deviceId;
private String deviceConncectionn;
	


	@JsonCreator
	public DeviceString(@JsonProperty("deviceId") String deviceId,@JsonProperty("deviceConnection") String deviceConncectionn)
	{
		this.deviceId = deviceId;
		this.deviceConncectionn = deviceConncectionn;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceConncectionn() {
	return deviceConncectionn;
}

   public void setDeviceConncectionn(String deviceConncectionn) {
	this.deviceConncectionn = deviceConncectionn;
}
}
