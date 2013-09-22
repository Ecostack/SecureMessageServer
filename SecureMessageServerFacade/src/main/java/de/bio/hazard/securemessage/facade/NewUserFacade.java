package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;
import de.bio.hazard.securemessage.dto.user.NewUserWebserviceDTO;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.helper.UserRoleType;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.UserRoleService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;

@Component
public class NewUserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired(required = true)
    private EncryptionObjectModifier encryptionObjectModifier;

    public void addNewUser(NewUserWebserviceDTO pNewUserWebserviceDTO) {
	User lcUser = transformToUser(pNewUserWebserviceDTO);
	userService.addUser(lcUser);
    }

    private User transformToUser(NewUserWebserviceDTO pNewUserWebserviceDTO) {
	User lcUser = new User();
	try {
	    Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	    byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(pNewUserWebserviceDTO.getSymEncryptionKey(),lcServerPrivateKey.getValue(), true);
	    lcUser.setRole(userRoleService.getUserRoleByType(UserRoleType.Registered));
	    lcUser.setUsername(encryptionObjectModifier.symmetricDecrypt(pNewUserWebserviceDTO.getUsername(), lcSymmetricKey));
	    lcUser.setPassword(encryptionObjectModifier.symmetricDecrypt(pNewUserWebserviceDTO.getPassword(), lcSymmetricKey));
	    lcUser.setEmail(encryptionObjectModifier.symmetricDecrypt(pNewUserWebserviceDTO.getEmail(), lcSymmetricKey));
	    lcUser.setPhonenumber(encryptionObjectModifier.symmetricDecrypt(pNewUserWebserviceDTO.getMobilenumber(), lcSymmetricKey));
	    lcUser.setName(encryptionObjectModifier.symmetricDecrypt(pNewUserWebserviceDTO.getName(), lcSymmetricKey));
	    lcUser.setPrename(encryptionObjectModifier.symmetricDecrypt(pNewUserWebserviceDTO.getPrename(), lcSymmetricKey));
	    lcUser.setPublicAsymmetricKey(encryptionObjectModifier.symmetricDecryptToByte(pNewUserWebserviceDTO.getPublicKeyForMessaging(),lcSymmetricKey));
	}
	catch (Exception e) {
	    // TODO handle exception
	    System.err.println(e.toString());
	}
	return lcUser;
    }
}
