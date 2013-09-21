package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceDTO;
import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceReturnDTO;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;

@Component
public class NewDeviceFacade {

    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ConfigService configService;

    @Autowired(required = true)
    private EncryptionObjectModifier encryptionObjectModifier;

    public NewDeviceWebserviceReturnDTO addNewDevice(NewDeviceWebserviceDTO pNewDeviceWebserviceDTO) {
	Device lcDevice = transformToDevice(pNewDeviceWebserviceDTO);
	String lcDeviceID = getDeviceService().addDeviceAndReturnDeviceId(lcDevice);
	return transformFromDeviceIdToReturn(lcDeviceID);
    }

    private NewDeviceWebserviceReturnDTO transformFromDeviceIdToReturn(String pDeviceId) {
	NewDeviceWebserviceReturnDTO lcReturn = new NewDeviceWebserviceReturnDTO();
	lcReturn.setDeviceId(pDeviceId);
	return lcReturn;
    }

    private Device transformToDevice(NewDeviceWebserviceDTO pNewDeviceWebserviceDTO) {
	Device lcDevice = new Device();
	
	try {
	    Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	    byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(pNewDeviceWebserviceDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(), true);
	    lcDevice.setDeviceDescription(encryptionObjectModifier.symmetricDecrypt(pNewDeviceWebserviceDTO.getDevicename(),lcSymmetricKey));
	    /* TODO Authentication User 
	     * by pNewDeviceWebserviceDTO.getPassword() and pNewDeviceWebserviceDTO.getUsername()
	     * XXX NicoH: oder wird das vorher schon gemacht und ich kann mich darauf verlassen?
	     */
	    lcDevice.setUser(userService.getUserByUsername(encryptionObjectModifier.symmetricDecrypt(pNewDeviceWebserviceDTO.getUsername(),lcSymmetricKey)));
	    lcDevice.setPublicAsyncKey(encryptionObjectModifier.symmetricDecryptToByte(pNewDeviceWebserviceDTO.getPublicKeyForDevice(),lcSymmetricKey));
	    lcDevice.setActivated(false);
	}
	catch (Exception e) {
	    // TODO handle exception
	    System.err.println(e.toString());
	}
	return lcDevice;
    }

    public DeviceService getDeviceService() {
	return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
	this.deviceService = deviceService;
    }

}
