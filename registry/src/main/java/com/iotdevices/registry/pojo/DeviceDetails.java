package com.iotdevices.registry.pojo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDetails {

	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("date") 
	private Date date;
	
	@JsonProperty("status") 
	private String status;
	
	@JsonProperty("version") 
	private String version;
	
	@JsonProperty("location") 
	private String location;
	
	@JsonProperty("datapoints") 
	private String datapoints;
	
	

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(String datapoints) {
		this.datapoints = datapoints;
	}

	
	
	
	
	
	
	
}
