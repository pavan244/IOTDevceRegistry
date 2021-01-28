package com.iotdevices.registry.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotdevices.registry.service.DataPoints;

public class UpdateDataPoins {

	@JsonProperty("id") 
	private String id;
	@JsonProperty("name") 
	private String name;
	@JsonProperty("description") 
	private String description;
	@JsonProperty("dataPoints") 
	private List<DataPoints> dataPoints;
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
	
	
	
	
	
}
