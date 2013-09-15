package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.user.NewUserWebserviceDTO;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.UserService;

@Component
public class NewUserFacade {

	@Autowired
	private UserService userService;

	public void addNewUser(NewUserWebserviceDTO pNewUserWebserviceDTO) {
		User lcUser = transformToUser(pNewUserWebserviceDTO);
		getUserService().addUser(lcUser);
	}
	
	private User transformToUser(NewUserWebserviceDTO pNewUserWebserviceDTO) {
		User lcUser = new User();
		lcUser.setUsername(pNewUserWebserviceDTO.getUsername());
		lcUser.setPassword(pNewUserWebserviceDTO.getPassword());
		lcUser.setEmail(pNewUserWebserviceDTO.getEmail());
		lcUser.setPhonenumber(pNewUserWebserviceDTO.getMobilenumber());
		lcUser.setName(pNewUserWebserviceDTO.getName());
		lcUser.setPrename(pNewUserWebserviceDTO.getPrename());
		lcUser.setPublicAsyncKey(pNewUserWebserviceDTO.getPublicKeyForMessaging());
		// TODO NicoH; Entschluesselung einbauen
		return lcUser;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
