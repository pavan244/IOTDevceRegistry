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
	@JsonProperty("date") 
	private String Date;
	@JsonProperty("status") 
	private String status;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("location")
	private String location;
	
	
	@JsonProperty("dataPoints") 
	private List<DataPoints> dataPoints;
	
	@JsonProperty("settings") 
    private List<DeviceSettings> deviceSettings;
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDate() {
		return Date;
	}


	public void setDate(String date) {
		Date = date;
	}


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
	
/*	@JsonCreator
	public DeviceInfo(@JsonProperty("id") String id,@JsonProperty("name") String name,@JsonProperty("description") String description)
			{
		        setId(id); 
		        setName(name);
		        setDescription(description);
		        
			}
*/

	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}
	
}
