package de.bio.hazard.securemessage.model;

import de.bio.hazard.securemessage.util.Statics;

public class AuthenticationToken extends AbstractToken {

	public AuthenticationToken(String tokenid) {
		super(tokenid);
	}
	
	@Override
	protected int getValidTimeInSeconds() {
		return Statics.AUTH_TOKEN_VALID_SECONDS;
	}

}
