package de.bio.hazard.securemessage.model.facade;

public class AuthenticationStepOne {

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
