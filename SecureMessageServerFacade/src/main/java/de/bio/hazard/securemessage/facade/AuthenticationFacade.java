package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepOneDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepOneReturnDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepTwoDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepTwoReturnDTO;
import de.bio.hazard.securemessage.facade.exception.DeviceNotFoundException;
import de.bio.hazard.securemessage.facade.exception.EncryptionException;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepOne;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepOneReturn;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepTwo;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepTwoReturn;
import de.bio.hazard.securemessage.service.AuthenticationService;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;

@Component
public class AuthenticationFacade {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private EncryptionObjectModifier encryptionObjectModifier;

	public AuthenticationStepOneReturnDTO authenticateStepOne(
			AuthenticationStepOneDTO pAuthenticationStepOneDTO)
			throws EncryptionException, DeviceNotFoundException {
		AuthenticationStepOneDTO lcDTO = pAuthenticationStepOneDTO;

		lcDTO = decryptAuthenticationStepOneDTO(lcDTO);

		AuthenticationStepOne lcServiceModel = transformStepOneDTOtoService(lcDTO);

		Device lcDevice = deviceService.getDeviceByDeviceId(lcServiceModel
				.getDeviceId());

		AuthenticationStepOneReturn lcReturn = authenticationService
				.authenticationStepOne(lcServiceModel);
		AuthenticationStepOneReturnDTO lcReturnDTO = transformStepOneReturnServiceToDTO(lcReturn);

		encryptAuthenticationStepOneReturnDTOByPublicKeyOfDevice(lcReturnDTO,
				lcDevice);

		return lcReturnDTO;
	}

	public AuthenticationStepTwoReturnDTO authenticateStepTwo(
			AuthenticationStepTwoDTO pAuthenticationStepTwoDTO)
			throws EncryptionException {
		AuthenticationStepTwoDTO lcAuthenticationStepTwoDTO = pAuthenticationStepTwoDTO;

		lcAuthenticationStepTwoDTO = decryptAuthenticationStepTwoDTO(lcAuthenticationStepTwoDTO);

		AuthenticationStepTwo lcServiceModel = transformStepTwoDTOtoService(lcAuthenticationStepTwoDTO);

		AuthenticationStepTwoReturn lcReturn = authenticationService
				.authenticationStepTwo(lcServiceModel);
		AuthenticationStepTwoReturnDTO lcReturnDTO = transformStepTwoReturnServiceToDTO(lcReturn);

		Device lcDevice = deviceService
				.getDeviceByDeviceId(authenticationService
						.getDeviceIdByTokenId(lcReturn.getTokenId()));

		lcReturnDTO = encryptAuthenticationStepTwoReturnDTOByPublicKeyOfDevice(
				lcReturnDTO, lcDevice);

		return lcReturnDTO;
	}

	private AuthenticationStepOneDTO decryptAuthenticationStepOneDTO(
			AuthenticationStepOneDTO pAuthenticationStepOneDTO)
			throws EncryptionException {

		AuthenticationStepOneDTO lcDTO = pAuthenticationStepOneDTO;

		try {
			Config lcServerPrivateKey = configService
					.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
			byte[] lcSymmetricKey;

			lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(
					lcDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(),
					true);

			lcDTO.setDeviceId(encryptionObjectModifier.symmetricDecrypt(
					lcDTO.getDeviceId(), lcSymmetricKey));

			lcDTO.setPassword(encryptionObjectModifier.symmetricDecrypt(
					lcDTO.getPassword(), lcSymmetricKey));
			lcDTO.setUsername(encryptionObjectModifier.symmetricDecrypt(
					lcDTO.getUsername(), lcSymmetricKey));
			lcDTO.setDate(encryptionObjectModifier.symmetricDecrypt(
					lcDTO.getDate(), lcSymmetricKey));

		} catch (Exception e) {
			// TODO SebastianS; Logging
			e.printStackTrace();
			throw new EncryptionException();
		}

		return pAuthenticationStepOneDTO;
	}

	private AuthenticationStepTwoDTO decryptAuthenticationStepTwoDTO(
			AuthenticationStepTwoDTO pAuthenticationStepTwoDTO)
			throws EncryptionException {

		AuthenticationStepTwoDTO lcDTO = pAuthenticationStepTwoDTO;

		try {
			Config lcServerPrivateKey = configService
					.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
			byte[] lcSymmetricKey;

			lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(
					lcDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(),
					true);

			lcDTO.setHandshakeId(encryptionObjectModifier.symmetricDecrypt(
					lcDTO.getHandshakeId(), lcSymmetricKey));
			lcDTO.setRandomHashedValue(encryptionObjectModifier
					.symmetricDecrypt(lcDTO.getRandomHashedValue(),
							lcSymmetricKey));
			lcDTO.setDate(encryptionObjectModifier.symmetricDecrypt(
					lcDTO.getDate(), lcSymmetricKey));

		} catch (Exception e) {
			// TODO SebastianS; Logging
			e.printStackTrace();
			throw new EncryptionException();
		}

		return pAuthenticationStepTwoDTO;
	}

	private AuthenticationStepOneReturnDTO encryptAuthenticationStepOneReturnDTOByPublicKeyOfDevice(
			AuthenticationStepOneReturnDTO pAuthenticationStepReturnDTO,
			Device pDevice) {
		// TODO NicoH; Verschluesselung

		return pAuthenticationStepReturnDTO;
	}

	private AuthenticationStepTwoReturnDTO encryptAuthenticationStepTwoReturnDTOByPublicKeyOfDevice(
			AuthenticationStepTwoReturnDTO pAuthenticationStepReturnDTO,
			Device pDevice) {
		// TODO NicoH; Verschluesselung
		return pAuthenticationStepReturnDTO;
	}

	private AuthenticationStepOne transformStepOneDTOtoService(
			AuthenticationStepOneDTO pTransform) {
		AuthenticationStepOne lcResult = new AuthenticationStepOne();
		lcResult.setDate(pTransform.getDate());
		lcResult.setDeviceId(pTransform.getDeviceId());
		lcResult.setPassword(pTransform.getPassword());
		lcResult.setUsername(pTransform.getUsername());

		return lcResult;
	}

	private AuthenticationStepTwo transformStepTwoDTOtoService(
			AuthenticationStepTwoDTO pTransform) {
		AuthenticationStepTwo lcResult = new AuthenticationStepTwo();
		lcResult.setDate(pTransform.getDate());
		lcResult.setHandshakeId(pTransform.getHandshakeId());
		lcResult.setRandomHashedValue(pTransform.getRandomHashedValue());

		return lcResult;
	}

	private AuthenticationStepOneReturnDTO transformStepOneReturnServiceToDTO(
			AuthenticationStepOneReturn pTransform) {

		AuthenticationStepOneReturnDTO lcResult = new AuthenticationStepOneReturnDTO();
		lcResult.setHandshakeId(pTransform.getHandshakeId());
		lcResult.setRandomHashedValue(pTransform.getRandomHashedValue());

		return lcResult;
	}

	private AuthenticationStepTwoReturnDTO transformStepTwoReturnServiceToDTO(
			AuthenticationStepTwoReturn pTransform) {

		AuthenticationStepTwoReturnDTO lcResult = new AuthenticationStepTwoReturnDTO();
		lcResult.setTokenId(pTransform.getTokenId());

		return lcResult;
	}

	// private AuthenticationStepOneReturnDTO getStepOneReturnDTO() {
	// AuthenticationStepOneReturnDTO lcDTO = new
	// AuthenticationStepOneReturnDTO();
	//
	// lcDTO.setHandshakeId(authenticationService.getNewHandshakeId);
	// lcDTO.setRandomHashedValue(authenticationService.getNextRandomNumber()
	// .toString());
	//
	// return lcDTO;
	// }

	// private AuthenticationStepTwoReturnDTO getStepTwoReturnDTO(Device
	// pDevice) {
	// AuthenticationStepTwoReturnDTO lcDTO = new
	// AuthenticationStepTwoReturnDTO();
	// lcDTO.setTokenId(getNewToken(pDevice).getTokenid());
	// return lcDTO;
	// }

	// private AuthenticationToken getNewToken(Device pDevice) {
	// AuthenticationToken lcToken = authenticationService
	// .getNewToken(pDevice);
	// return lcToken;
	// }
}
