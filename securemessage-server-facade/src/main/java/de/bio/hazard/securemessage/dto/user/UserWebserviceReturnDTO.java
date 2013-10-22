package de.bio.hazard.securemessage.dto.user;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class UserWebserviceReturnDTO extends AbstractDTO {

	private String publicKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
