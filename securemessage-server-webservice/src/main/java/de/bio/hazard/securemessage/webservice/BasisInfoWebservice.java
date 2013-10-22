package de.bio.hazard.securemessage.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.basisinfo.ServerPublicKeyDTO;
import de.bio.hazard.securemessage.facade.BasisInfoFacade;

@WebService
@Component
@javax.xml.ws.BindingType(value=javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class BasisInfoWebservice {

	@Autowired
	private BasisInfoFacade basisInfoFacade;

	public ServerPublicKeyDTO getServerPublicKey() {
		return basisInfoFacade.getServerPublicKey();
	}
}
