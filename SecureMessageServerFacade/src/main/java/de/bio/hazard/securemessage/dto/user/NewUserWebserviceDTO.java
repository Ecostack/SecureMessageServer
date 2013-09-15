package de.bio.hazard.securemessage.dto.user;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class NewUserWebserviceDTO extends AbstractDTO{
	private String username;
	private String password;
	private String email;
	private String mobilenumber;
	private String name;
	private String prename;
	private byte[] publicKeyForMessaging;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public byte[] getPublicKeyForMessaging() {
		return publicKeyForMessaging;
	}

	public void setPublicKeyForMessaging(byte[] publicKeyForMessaging) {
		this.publicKeyForMessaging = publicKeyForMessaging;
	}

}
