package de.bio.hazard.securemessage.facade;

import javax.print.attribute.standard.Severity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.ServerPublicKeyDTO;
import de.bio.hazard.securemessage.service.ConfigService;

@Component
public class BasisInfoFacade {

	@Autowired
	private ConfigService configService;
	
	public ServerPublicKeyDTO getServerPublicKey() {
		ServerPublicKeyDTO lcReturn = new ServerPublicKeyDTO();
		lcReturn.setServerPublicKey(new byte[] {51,56});
		return lcReturn;
	}
}
