package com.iotdevices.registry.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String guid;

	

	public String getGuid() {
		return guid;
	}

	public JwtResponse(String jwttoken,String guid) {
		this.jwttoken = jwttoken;
		this.guid = guid;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
