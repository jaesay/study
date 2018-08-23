package com.myspring.domain;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class RememberMeToken {
	private String series;
	private String username;
	private String token;
	private Date lastUsed;
	public RememberMeToken(PersistentRememberMeToken token) {
		this.series = token.getSeries();
		this.username = token.getUsername();
		this.token = token.getTokenValue();
		this.lastUsed = token.getDate();
	}
	public RememberMeToken() {}
	
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
}
