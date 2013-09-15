package de.bio.hazard.securemessage.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.NewUserWebserviceDTO;
import de.bio.hazard.securemessage.facade.NewUserFacade;
import de.bio.hazard.securemessage.service.implementation.DefaultMessageService;

@WebService
@Component
public class AuthenticationWebservice  {

	@Autowired
	private NewUserFacade newUserFacade;
	
	@WebMethod
	public void addNewUser(NewUserWebserviceDTO pNewUser) {
		newUserFacade.addNewUser(pNewUser);
	}

}
