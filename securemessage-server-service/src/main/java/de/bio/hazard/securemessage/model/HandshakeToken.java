package de.bio.hazard.securemessage.model;

import de.bio.hazard.securemessage.util.Statics;

public class HandshakeToken extends AbstractToken {

	private String randomHashValue = "";

	public HandshakeToken(String tokenid) {

		super(tokenid);
	}

	@Override
	protected int getValidTimeInSeconds() {
		return Statics.HANDSHAKE_TOKEN_VALID_SECONDS;
	}

	public String getRandomHashValue() {
		return randomHashValue;
	}

	public void setRandomHashValue(String randomHashValue) {
		this.randomHashValue = randomHashValue;
	}
}
