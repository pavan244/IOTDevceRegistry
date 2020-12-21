package com.iotdevices.registry.pojo;

public class LoginResponse {
private String guid ;
private String token;

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getGuid() {
	return guid;
}

public void setGuid(String guid) {
	this.guid = guid;
}
}
