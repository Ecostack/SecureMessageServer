package de.bio.hazard.securemessage.dto.user;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class UserWebserviceDTO extends AbstractDTO {

	private String username;
	private String mobilenumber;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
}
