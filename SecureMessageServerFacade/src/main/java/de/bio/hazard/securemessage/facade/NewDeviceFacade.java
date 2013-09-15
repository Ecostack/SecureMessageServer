package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceDTO;
import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceReturnDTO;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.service.DeviceService;

@Component
public class NewDeviceFacade {

	@Autowired
	private DeviceService deviceService;

	public NewDeviceWebserviceReturnDTO addNewDevice(
			NewDeviceWebserviceDTO pNewDeviceWebserviceDTO) {
		Device lcDevice = transformToDevice(pNewDeviceWebserviceDTO);
		String lcDeviceID = getDeviceService().addDeviceAndReturnDeviceId(
				lcDevice);

		return transformFromDeviceIdToReturn(lcDeviceID);
	}

	private NewDeviceWebserviceReturnDTO transformFromDeviceIdToReturn(
			String pDeviceId) {

		NewDeviceWebserviceReturnDTO lcReturn = new NewDeviceWebserviceReturnDTO();
		lcReturn.setDeviceId(pDeviceId);
		return lcReturn;
	}

	private Device transformToDevice(
			NewDeviceWebserviceDTO pNewUserWebserviceDTO) {
		Device lcDevice = new Device();
		// lcUser.setUsername(pNewUserWebserviceDTO.getUsername());
		// lcUser.setPassword(pNewUserWebserviceDTO.getPassword());
		// lcUser.setEmail(pNewUserWebserviceDTO.getEmail());
		// lcUser.setPhonenumber(pNewUserWebserviceDTO.getMobilenumber());
		// lcUser.setName(pNewUserWebserviceDTO.getName());
		// lcUser.setPrename(pNewUserWebserviceDTO.getPrename());
		// lcUser.setPublicAsyncKey(pNewUserWebserviceDTO
		// .getPublicKeyForMessaging());
		// TODO NicoH; Entschluesselung einbauen
		return lcDevice;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

}
