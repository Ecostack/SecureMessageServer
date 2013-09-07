package de.bio.hazard.securemessage.model;

import javax.persistence.Column;

public class MessageContentAttachment extends MessageContent {

	@Column(unique = false, nullable = false)
	private String filename = "";

	@Column(unique = false, nullable = false)
	private int runningNumber = 0;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getRunningNumber() {
		return runningNumber;
	}

	public void setRunningNumber(int runningNumber) {
		this.runningNumber = runningNumber;
	}
}
