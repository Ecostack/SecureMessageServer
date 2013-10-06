package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.user.UserWebserviceDTO;
import de.bio.hazard.securemessage.dto.user.UserWebserviceReturnDTO;
import de.bio.hazard.securemessage.facade.exception.UserNotFoundException;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.AuthenticationService;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;
import de.bio.hazard.securemessage.tecframework.encryption.symmetric.SymmetricKeygen;
import de.bio.hazard.securemessage.tecframework.exception.EncryptionExceptionBiohazard;

@Component
public class UserFacade {

    @Autowired
    private UserService userService;

    @Autowired(required = true)
    private ConfigService configService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SymmetricKeygen symmetricKeygen;

    @Autowired(required = true)
    private EncryptionObjectModifier encryptionObjectModifier;

    public UserWebserviceReturnDTO getUserByUsername(UserWebserviceDTO pUserWebserviceDTO) throws UserNotFoundException {
	try {
	    return transformToUserWebserviceReturnDTOByUsername(pUserWebserviceDTO);
	}
	catch (Exception e) {
	    // TODO SebastianS; Logging
	    e.printStackTrace();
	}
	throw new UserNotFoundException();
    }

    private UserWebserviceReturnDTO transformToUserWebserviceReturnDTOByUsername(UserWebserviceDTO pUserWebserviceDTO) throws EncryptionExceptionBiohazard {
	UserWebserviceDTO lcUserWebserviceDTO = decryptUserWebserviceDTO(pUserWebserviceDTO);
	UserWebserviceReturnDTO lcUserWebserviceReturnDTO = new UserWebserviceReturnDTO();
	User lcUser = userService.getUserByUsername(lcUserWebserviceDTO.getUsername());
	lcUserWebserviceReturnDTO.setPublicKey(encryptionObjectModifier.encodeBase64(lcUser.getPublicKeyForMessaging()));
	lcUserWebserviceReturnDTO = encryptUserWebserviceReturnDTO(lcUserWebserviceReturnDTO, authenticationService.getDeviceIdByTokenId(lcUserWebserviceDTO.getTokenId()));
	return lcUserWebserviceReturnDTO;
    }

    private UserWebserviceReturnDTO encryptUserWebserviceReturnDTO(UserWebserviceReturnDTO pUserWebserviceReturnDTO, String pDeviceTokenID) throws EncryptionExceptionBiohazard {
	UserWebserviceReturnDTO lcUserWebserviceReturnDTO = pUserWebserviceReturnDTO;
	try {
	    byte[] lcDevicePublicKey = deviceService.getDeviceByDeviceId(pDeviceTokenID).getPublicAsyncKey();
	    byte[] lcSymmetricKey = symmetricKeygen.getKey(128);
	    lcUserWebserviceReturnDTO.setSymEncryptionKey(encryptionObjectModifier.asymmetricEncrypt(lcSymmetricKey, lcDevicePublicKey, false));
	    lcUserWebserviceReturnDTO.setPublicKey(encryptionObjectModifier.symmetricEncrypt(pUserWebserviceReturnDTO.getPublicKey(), lcSymmetricKey));
	}
	catch (Exception e) {
	    // TODO SebastianS; Logging
	    e.printStackTrace();
	    throw new EncryptionExceptionBiohazard();
	}
	return lcUserWebserviceReturnDTO;
    }

    private UserWebserviceDTO decryptUserWebserviceDTO(UserWebserviceDTO pUserWebserviceDTO) throws EncryptionExceptionBiohazard {
	UserWebserviceDTO lcUserWebserviceDTO = new UserWebserviceDTO();
	try {
	    Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	    byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(pUserWebserviceDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(), true);

	    lcUserWebserviceDTO.setUsername(encryptionObjectModifier.symmetricDecrypt(pUserWebserviceDTO.getUsername(), lcSymmetricKey));
	    lcUserWebserviceDTO.setMobilenumber(encryptionObjectModifier.symmetricDecrypt(pUserWebserviceDTO.getMobilenumber(), lcSymmetricKey));
	    lcUserWebserviceDTO.setTokenId(encryptionObjectModifier.symmetricDecrypt(pUserWebserviceDTO.getTokenId(), lcSymmetricKey));

	}
	catch (Exception e) {
	    // TODO SebastianS; Logging
	    e.printStackTrace();
	    throw new EncryptionExceptionBiohazard();
	}
	return lcUserWebserviceDTO;
    }
}
