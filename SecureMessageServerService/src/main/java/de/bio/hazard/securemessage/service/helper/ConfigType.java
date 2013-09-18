package de.bio.hazard.securemessage.service.helper;

public enum ConfigType {
	SERVER_PUBLIC_KEY(10), SERVER_PRIVATE_KEY(20);

	private int runningNumber;

	ConfigType(int pVal) {
		runningNumber = pVal;
	}

	public int getRunningNumber() {
		return runningNumber;
	}
}
