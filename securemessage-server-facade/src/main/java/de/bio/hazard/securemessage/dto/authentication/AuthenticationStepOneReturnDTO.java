package de.bio.hazard.securemessage.dto.authentication;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class AuthenticationStepOneReturnDTO extends AbstractDTO {
	private String randomHashedValue;
	private String handshakeId;

	public String getRandomHashedValue() {
		return randomHashedValue;
	}

	public void setRandomHashedValue(String randomHashedValue) {
		this.randomHashedValue = randomHashedValue;
	}

	public String getHandshakeId() {
		return handshakeId;
	}

	public void setHandshakeId(String handshakeid) {
		this.handshakeId = handshakeid;
	}

}
