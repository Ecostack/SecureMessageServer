package de.bio.hazard.securemessage.model;

import java.util.Calendar;

import de.bio.hazard.securemessage.util.Statics;

public class AuthenticationToken extends AbstractToken {

	public AuthenticationToken() {
		getInvalidAt().add(Calendar.SECOND, Statics.TOKEN_VALID_SECONDS);
	}

	public AuthenticationToken(String tokenid) {
		super(tokenid);
	}

}
