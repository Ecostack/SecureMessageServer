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
import de.bio.hazard.securemessage.tecframework.encryption.symmetric.SymmetricKeygen;

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
	
	@Autowired
	private SymmetricKeygen symmetricKeygen;

	public NewDeviceWebserviceReturnDTO addNewDevice(
			NewDeviceWebserviceDTO pNewDeviceWebserviceDTO) {
		Device lcDevice = transformToDevice(pNewDeviceWebserviceDTO);
		System.err.println("device pub key server : "
				+ encryptionObjectModifier.encodeBase64(lcDevice.getPublicAsyncKey()));
		
		String lcDeviceID = getDeviceService().addDeviceAndReturnDeviceId(
				lcDevice);

		NewDeviceWebserviceReturnDTO lcReturnDTO = transformFromDeviceIdToReturn(lcDeviceID,lcDevice.getPublicAsyncKey());
		return lcReturnDTO;
	}

	private NewDeviceWebserviceReturnDTO transformFromDeviceIdToReturn(
			String pDeviceId, byte[] pDevicePublicKey) {
		NewDeviceWebserviceReturnDTO lcReturn = new NewDeviceWebserviceReturnDTO();

		try {
			byte[] lcSymmetricKey = symmetricKeygen.getKey(128);
			lcReturn
					.setSymEncryptionKey(encryptionObjectModifier
							.asymmetricEncrypt(lcSymmetricKey,
									pDevicePublicKey, false));
			lcReturn.setDeviceId(encryptionObjectModifier
					.symmetricEncrypt(pDeviceId,
							lcSymmetricKey));
		} catch (Exception e) {

		}
		return lcReturn;
	}

	private Device transformToDevice(
			NewDeviceWebserviceDTO pNewDeviceWebserviceDTO) {
		Device lcDevice = new Device();

		try {
			Config lcServerPrivateKey = configService
					.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
			byte[] lcSymmetricKey = encryptionObjectModifier
					.asymmetricDecryptToByte(
							pNewDeviceWebserviceDTO.getSymEncryptionKey(),
							lcServerPrivateKey.getValue(), true);
			lcDevice.setDeviceDescription(encryptionObjectModifier
					.symmetricDecrypt(pNewDeviceWebserviceDTO.getDevicename(),
							lcSymmetricKey));
			/*
			 * TODO Authentication User by pNewDeviceWebserviceDTO.getPassword()
			 * and pNewDeviceWebserviceDTO.getUsername() XXX NicoH: oder wird
			 * das vorher schon gemacht und ich kann mich darauf verlassen?
			 */
			lcDevice.setUser(userService
					.getUserByUsername(encryptionObjectModifier
							.symmetricDecrypt(
									pNewDeviceWebserviceDTO.getUsername(),
									lcSymmetricKey)));
			lcDevice.setPublicAsyncKey(encryptionObjectModifier
					.symmetricDecryptToByte(
							pNewDeviceWebserviceDTO.getPublicKeyForDevice(),
							lcSymmetricKey));
			lcDevice.setActivated(false);
		} catch (Exception e) {
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
