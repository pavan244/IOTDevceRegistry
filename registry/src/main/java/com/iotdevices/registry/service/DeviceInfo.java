package com.iotdevices.registry.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfo {
	private String id;
	private String name;
	private String description;
	private List<DataPoints> dataPoints;
	private List<DeviceSettings> deviceSettings;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	
	public List<DataPoints> getDataPoints() {
		return dataPoints;
	}


	public void setDataPoints(List<DataPoints> dataPoints) {
		this.dataPoints = dataPoints;
	}


	public List<DeviceSettings> getDeviceSettings() {
		return deviceSettings;
	}


	public void setDeviceSettings(List<DeviceSettings> deviceSettings) {
		this.deviceSettings = deviceSettings;
	}


	
	
	@JsonCreator
	public DeviceInfo(@JsonProperty("id") String id,@JsonProperty("name") String name,@JsonProperty("description") String description,
			@JsonProperty("dataPoints") List<DataPoints> dataPoints,@JsonProperty("settings") List<DeviceSettings> deviceSettings)
			{
		        setId(id); 
		        setName(name);
		        setDescription(description);
		        setDataPoints(dataPoints);
		        setDeviceSettings(deviceSettings);
			}
	
	
	
}
