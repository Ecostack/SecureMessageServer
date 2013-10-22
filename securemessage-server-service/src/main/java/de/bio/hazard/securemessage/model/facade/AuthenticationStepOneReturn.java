package de.bio.hazard.securemessage.model.facade;

public class AuthenticationStepOneReturn {
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
