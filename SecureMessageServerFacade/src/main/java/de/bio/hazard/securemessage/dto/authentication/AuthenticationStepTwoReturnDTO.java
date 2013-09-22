package de.bio.hazard.securemessage.dto.authentication;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class AuthenticationStepTwoReturnDTO extends AbstractDTO {
	private String tokenId;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
