package com.sahul.jwt.payload.response;

import java.util.List;

public class jwtResponse {
	private String token;
	private String type="Bearer";
	private Long id;
	List<String> roles;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public jwtResponse(String token, String type, Long id, List<String> roles) {
		this.token = token;
		this.type = type;
		this.id = id;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	

}
