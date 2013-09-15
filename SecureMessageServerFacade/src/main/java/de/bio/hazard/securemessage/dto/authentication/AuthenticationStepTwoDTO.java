package de.bio.hazard.securemessage.dto.authentication;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class AuthenticationStepTwoDTO extends AbstractDTO {

	private String randomHashedValue;
	private String handshakeId;
	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long pTimestamp) {
		this.timestamp = pTimestamp;
	}

	public String getRandomHashedValue() {
		return randomHashedValue;
	}

	public void setRandomHashedValue(String randomHashedValue) {
		this.randomHashedValue = randomHashedValue;
	}

	public String getHandshakeId() {
		return handshakeId;
	}

	public void setHandshakeId(String handshakeId) {
		this.handshakeId = handshakeId;
	}

}
