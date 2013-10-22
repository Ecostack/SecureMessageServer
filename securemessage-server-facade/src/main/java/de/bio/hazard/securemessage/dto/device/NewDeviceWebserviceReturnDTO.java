package de.bio.hazard.securemessage.dto.device;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class NewDeviceWebserviceReturnDTO extends AbstractDTO {

	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
