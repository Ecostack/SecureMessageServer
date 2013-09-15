package de.bio.hazard.securemessage.dto.authentication;

import de.bio.hazard.securemessage.dto.AbstractDTO;
import de.bio.hazard.securemessage.model.AuthenticationToken;

public class AuthenticationStepTwoReturnDTO extends AbstractDTO {
	private AuthenticationToken token;

	public AuthenticationToken getToken() {
		return token;
	}

	public void setToken(AuthenticationToken token) {
		this.token = token;
	}

}
