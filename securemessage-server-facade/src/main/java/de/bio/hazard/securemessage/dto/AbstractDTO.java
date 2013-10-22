package de.bio.hazard.securemessage.dto;

public class AbstractDTO {

	private String symEncryptionKey;
	private String tokenId;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getSymEncryptionKey() {
		return symEncryptionKey;
	}

	public void setSymEncryptionKey(String symEncryptionKey) {
		this.symEncryptionKey = symEncryptionKey;
	}

}
