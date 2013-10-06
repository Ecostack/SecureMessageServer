package de.bio.hazard.securemessage.dto.message;

public class MessageContentKeyWebserviceDTO {

	private String username;
	private String symmetricEncryptionKey;

	public String getUsername() {
	    return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}

	public String getSymmetricEncryptionKey() {
	    return symmetricEncryptionKey;
	}

	public void setSymmetricEncryptionKey(String symmetricEncryptionKey) {
	    this.symmetricEncryptionKey = symmetricEncryptionKey;
	}	
}
