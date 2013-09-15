package de.bio.hazard.securemessage.dto;

public class AbstractDTO {

	private byte[] symEncryptionKey;

	public byte[] getSymEncryptionKey() {
		return symEncryptionKey;
	}
	public void setSymEncryptionKey(byte[] symEncryptionKey) {
		this.symEncryptionKey = symEncryptionKey;
	}
	
}
