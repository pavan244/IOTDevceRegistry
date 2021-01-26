package com.iotdevices.registry.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceMessageDetails {

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("date") 
	private String Date;
	@JsonProperty("status") 
	private String status;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("messageId")
	private String messageId;
	
	@JsonProperty("timeStamp")
	private String timestamp;
	@JsonProperty("type")
	private String type;
	@JsonProperty("message")
	private String message;
	@JsonProperty("loc")
	private String loc;
	
	@JsonProperty("comparedmessage")
	private String comparedmessage;
	
	public String getComparedmessage() {
		return comparedmessage;
	}
	public void setComparedmessage(String comparedmessage) {
		this.comparedmessage = comparedmessage;
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
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
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
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}

	
}
