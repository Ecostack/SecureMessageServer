package de.bio.hazard.securemessage.facade;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.user.NewUserWebserviceDTO;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.UserRoleService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;
import de.bio.hazard.securemessage.tecframework.exception.EncryptionExceptionBiohazard;

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

	public void addNewUser(NewUserWebserviceDTO pNewUserWebserviceDTO)
			throws EncryptionExceptionBiohazard {
		decryptNewUserWebserviceDTO(pNewUserWebserviceDTO);
		User lcUser = transformToUserDynamic(pNewUserWebserviceDTO);
		userService.addUser(lcUser);
	}

	private User transformToUserDynamic(
			NewUserWebserviceDTO pNewUserWebserviceDTO) {
		User lcUser = new User();

		BeanUtils.copyProperties(pNewUserWebserviceDTO, lcUser);
		return lcUser;
	}

	private void decryptNewUserWebserviceDTO(
			NewUserWebserviceDTO pNewUserWebserviceDTO)
			throws EncryptionExceptionBiohazard {
		Config lcServerPrivateKey = configService
				.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
		try {
			byte[] lcSymmetricKey = encryptionObjectModifier
					.asymmetricDecryptToByte(
							pNewUserWebserviceDTO.getSymEncryptionKey(),
							lcServerPrivateKey.getValue(), true);
			pNewUserWebserviceDTO.setUsername(encryptionObjectModifier
					.symmetricDecrypt(pNewUserWebserviceDTO.getUsername(),
							lcSymmetricKey));
			pNewUserWebserviceDTO.setPassword(encryptionObjectModifier
					.symmetricDecrypt(pNewUserWebserviceDTO.getPassword(),
							lcSymmetricKey));
			pNewUserWebserviceDTO.setEmail(encryptionObjectModifier
					.symmetricDecrypt(pNewUserWebserviceDTO.getEmail(),
							lcSymmetricKey));
			pNewUserWebserviceDTO.setTelephone(encryptionObjectModifier
					.symmetricDecrypt(pNewUserWebserviceDTO.getTelephone(),
							lcSymmetricKey));
			pNewUserWebserviceDTO.setName(encryptionObjectModifier
					.symmetricDecrypt(pNewUserWebserviceDTO.getName(),
							lcSymmetricKey));
			pNewUserWebserviceDTO.setPrename(encryptionObjectModifier
					.symmetricDecrypt(pNewUserWebserviceDTO.getPrename(),
							lcSymmetricKey));
			pNewUserWebserviceDTO
					.setPublicKeyForMessaging(encryptionObjectModifier.symmetricDecrypt(
							pNewUserWebserviceDTO.getPublicKeyForMessaging(),
							lcSymmetricKey));

		} catch (Exception e) {
			// TODO handle exception
			throw new EncryptionExceptionBiohazard();
		}

	}

	// private User transformToUser(NewUserWebserviceDTO pNewUserWebserviceDTO)
	// {
	// User lcUser = new User();
	// // try {
	// // TODO decrypt-Methode statt alles zusammen
	// // Config lcServerPrivateKey = configService
	// // .getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	// // byte[] lcSymmetricKey = encryptionObjectModifier
	// // .asymmetricDecryptToByte(
	// // pNewUserWebserviceDTO.getSymEncryptionKey(),
	// // lcServerPrivateKey.getValue(), true);
	// lcUser.setRole(userRoleService
	// .getUserRoleByType(UserRoleType.Registered));
	// lcUser.setUsername(pNewUserWebserviceDTO.getUsername());
	// lcUser.setPassword(pNewUserWebserviceDTO.getPassword());
	// lcUser.setEmail(pNewUserWebserviceDTO.getEmail());
	// lcUser.setTelephone(pNewUserWebserviceDTO.getTelephone());
	// lcUser.setName(pNewUserWebserviceDTO.getName());
	// lcUser.setPrename(pNewUserWebserviceDTO.getPrename());
	// lcUser.setPublicAsymmetricKey(pNewUserWebserviceDTO
	// .getSymEncryptionKey().getBytes());
	// // } catch (Exception e) {
	// // // TODO handle exception
	// // System.err.println(e.toString());
	// // }
	// return lcUser;
	// }
}
