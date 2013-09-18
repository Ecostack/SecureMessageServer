package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.ServerPublicKeyDTO;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.helper.ConfigType;

@Component
public class BasisInfoFacade {

	@Autowired
	private ConfigService configService;

	public ServerPublicKeyDTO getServerPublicKey() {
		Config lcConfig = configService
				.getConfigByEnumType(ConfigType.SERVER_PUBLIC_KEY);

		ServerPublicKeyDTO lcDTO = new ServerPublicKeyDTO();
		lcDTO.setServerPublicKey(lcConfig.getValue());

		return lcDTO;
	}
}
