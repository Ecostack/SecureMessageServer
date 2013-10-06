package de.bio.hazard.securemessage.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.user.UserListWebserviceDTO;
import de.bio.hazard.securemessage.dto.user.UserListWebserviceReturnDTO;
import de.bio.hazard.securemessage.dto.user.UserWebserviceDTO;
import de.bio.hazard.securemessage.dto.user.UserWebserviceReturnDTO;
import de.bio.hazard.securemessage.facade.UserFacade;
import de.bio.hazard.securemessage.facade.exception.UserNotFoundException;

@WebService
@Component
public class UserWebservice {

    @Autowired
    private UserFacade userFacade;

	@WebMethod
	public UserWebserviceReturnDTO getPublicKeyByUsername(
			UserWebserviceDTO pUserDTO) throws UserNotFoundException {
		return userFacade.getUserByUsername(pUserDTO);
	}

	public ArrayList<UserListWebserviceReturnDTO> getUsers(
			UserListWebserviceDTO pUsersDTO) {
		return userFacade.getUsers(pUsersDTO);
	}
}
