package de.bio.hazard.securemessage.dto.user;

import java.util.Calendar;

public class UserListItemWebserviceReturnDTO {

	private String username;

	private String publicKeyForMessagingAsBase64;

	private Calendar lastLoginAt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Calendar getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Calendar lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public String getPublicKeyForMessagingAsBase64() {
		return publicKeyForMessagingAsBase64;
	}

	public void setPublicKeyForMessagingAsBase64(
			String publicKeyForMessagingAsBase64) {
		this.publicKeyForMessagingAsBase64 = publicKeyForMessagingAsBase64;
	}
}
