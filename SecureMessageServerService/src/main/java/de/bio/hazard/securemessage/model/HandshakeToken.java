package de.bio.hazard.securemessage.model;

import java.util.Calendar;

import de.bio.hazard.securemessage.util.Statics;

public class HandshakeToken extends AbstractToken {

	private String randomHashValue = "";

	public HandshakeToken() {
		getInvalidAt().add(Calendar.SECOND, Statics.HANDSHAKE_VALID_SECONDS);
	}

	public HandshakeToken(String tokenid) {
		super(tokenid);
	}

	public String getRandomHashValue() {
		return randomHashValue;
	}

	public void setRandomHashValue(String randomHashValue) {
		this.randomHashValue = randomHashValue;
	}
}
