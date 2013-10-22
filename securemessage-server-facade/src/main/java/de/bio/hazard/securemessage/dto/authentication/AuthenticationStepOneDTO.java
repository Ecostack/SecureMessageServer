package de.bio.hazard.securemessage.dto.authentication;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class AuthenticationStepOneDTO extends AbstractDTO {

	private String username;
	private String password;
	private String date;
	private String deviceId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String pUsername) {
		this.username = pUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pPassword) {
		this.password = pPassword;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String pDeviceid) {
		this.deviceId = pDeviceid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
