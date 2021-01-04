package com.iotdevices.registry.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceSettings {
	  private String id;
	  private String name;
	  private String value;
	  private String unit;
	  
	  @JsonCreator
		public DeviceSettings(@JsonProperty("id") String id,@JsonProperty("name") String name,@JsonProperty("value") String value,
				@JsonProperty("unit") String unit)
				{
			        setId(id); 
			        setName(name);
			        setValue(value);
			        setUnit(unit);
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
