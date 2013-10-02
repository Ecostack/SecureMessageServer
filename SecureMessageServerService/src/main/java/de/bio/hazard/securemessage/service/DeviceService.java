package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.User;

public interface DeviceService {

	void addDevice(Device pDevice);

	void updateDevice(Device pDevice);

	void deleteDevice(Device pDevice);

	Device getDeviceById(long pID);

	List<Device> getDevices();

	List<Device> getDevicesByUser(User pUser);
	
	String addDeviceAndReturnDeviceId(Device pDevice);
	
	Device getDeviceByDeviceId(String pDeviceId);
	
	long getCountByDeviceId(String pDeviceId);
		
}
