package de.bio.hazard.securemessage.dto.user;

import java.util.Calendar;

public class UserListWebserviceReturnDTO {

	private String username;

	private byte[] publicKeyForMessaging;

	private Calendar lastLoginAt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPublicKeyForMessaging() {
		return publicKeyForMessaging;
	}

	public void setPublicKeyForMessaging(byte[] publicKeyForMessaging) {
		this.publicKeyForMessaging = publicKeyForMessaging;
	}

	public Calendar getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Calendar lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}
}
