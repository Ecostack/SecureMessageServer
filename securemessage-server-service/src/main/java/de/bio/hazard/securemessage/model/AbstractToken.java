package de.bio.hazard.securemessage.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class AbstractToken {

	private String tokenid = null;
	private Calendar created = Calendar.getInstance();
	private Calendar invalidAt = Calendar.getInstance();
	private String deviceId = "";

	public boolean isInvalid() {
		Calendar lcCal = Calendar.getInstance();

		SimpleDateFormat lcSDF = new SimpleDateFormat("hh:mm:ss SSS");
		System.err.println("current: " + lcSDF.format(lcCal.getTime()));
		System.err.println("invalidAt: " + lcSDF.format(invalidAt.getTime()));

		if (lcCal.after(invalidAt)) {
			return true;
		}
		return false;
	}

	public AbstractToken(String tokenid) {
		this();
		this.tokenid = tokenid;
	}

	public AbstractToken() {
		getInvalidAt().setTimeInMillis(
				getInvalidAt().getTimeInMillis()
						+ (getValidTimeInSeconds() * 1000));
	}

	protected abstract int getValidTimeInSeconds();

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
