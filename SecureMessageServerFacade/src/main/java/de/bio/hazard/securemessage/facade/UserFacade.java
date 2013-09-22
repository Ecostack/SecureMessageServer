package de.bio.hazard.securemessage.facade;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.user.UserWebserviceDTO;
import de.bio.hazard.securemessage.dto.user.UserWebserviceReturnDTO;
import de.bio.hazard.securemessage.facade.exception.UserNotFoundException;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;

@Component
public class UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigService configService;

    @Autowired(required = true)
    private EncryptionObjectModifier encryptionObjectModifier;

    public UserWebserviceReturnDTO getUserByUsername(UserWebserviceDTO pUserWebserviceDTO) throws UserNotFoundException {
	try {
	    return transformToUserWebserviceReturnDTO(pUserWebserviceDTO);
	}
	catch (Exception e) {
	    // TODO handle exception
	    System.err.println(e.toString());
	}
	throw new UserNotFoundException();
    }

    private UserWebserviceReturnDTO transformToUserWebserviceReturnDTO(UserWebserviceDTO pUserWebserviceDTO) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(pUserWebserviceDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(), true);
	UserWebserviceReturnDTO lcUserWebserviceReturnDTO=new UserWebserviceReturnDTO();
	User lcUser=userService.getUserByUsername(encryptionObjectModifier.symmetricDecrypt(pUserWebserviceDTO.getUsername(), lcSymmetricKey));
	//TODO über Token das Device suchen und mit dessen PK verschlüsseln (symmetrisch)
	lcUserWebserviceReturnDTO.setPublicKey(encryptionObjectModifier.encodeBase64(lcUser.getPublicAsymmetricKey()));
	return lcUserWebserviceReturnDTO;
    }
}
