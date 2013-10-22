package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.basisinfo.ServerPublicKeyDTO;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;

@Component
public class BasisInfoFacade {

	@Autowired
	private ConfigService configService;

	@Autowired
	private EncryptionObjectModifier encryptionObjectModifier;

	public ServerPublicKeyDTO getServerPublicKey() {
		Config lcConfig = configService
				.getConfigByEnumType(ConfigType.SERVER_PUBLIC_KEY);

		ServerPublicKeyDTO lcDTO = new ServerPublicKeyDTO();
		lcDTO.setServerPublicKeyAsBase64(encryptionObjectModifier
				.encodeBase64(lcConfig.getValue()));

		return lcDTO;
	}
}
