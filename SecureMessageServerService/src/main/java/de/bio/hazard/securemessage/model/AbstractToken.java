package de.bio.hazard.securemessage.model;

import java.util.Calendar;

public class AbstractToken {

	private String tokenid = null;
	private Calendar created = Calendar.getInstance();
	private Calendar invalidAt = Calendar.getInstance();
	private String deviceId = "";

	public boolean isInvalid() {
		Calendar lcCal = Calendar.getInstance();
		if (lcCal.compareTo(invalidAt) == 1) {
			return true;
		}
		return false;
	}

	public AbstractToken(String tokenid) {
		this();
		this.tokenid = tokenid;
	}

	public AbstractToken() {
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public Calendar getCreated() {
		return created;
	}

	public Calendar getInvalidAt() {
		return invalidAt;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
