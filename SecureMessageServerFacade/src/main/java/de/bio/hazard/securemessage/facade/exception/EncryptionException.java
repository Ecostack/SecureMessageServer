package de.bio.hazard.securemessage.facade.exception;

public class EncryptionException extends Exception {

	
	public EncryptionException() {
		super("Problem with encryption / decryption occured.");
	}
}
