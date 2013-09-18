package de.bio.hazard.securemessage.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepOneDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepOneReturnDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepTwoDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepTwoReturnDTO;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricKeygen;

@Component
public class AuthenticationFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private DeviceService deviceService;
	
	@Autowired(required=true)
	private AsymmetricKeygen asyncKeygen;

	public AuthenticationStepOneReturnDTO authenticateStepOne(
			AuthenticationStepOneDTO pAuthenticationStepOneDTO) {
		return new AuthenticationStepOneReturnDTO();
	}

	public AuthenticationStepTwoReturnDTO authenticateStepTwo(
			AuthenticationStepTwoDTO pAuthenticationStepTwoDTO) {
		return new AuthenticationStepTwoReturnDTO();
	}
}
